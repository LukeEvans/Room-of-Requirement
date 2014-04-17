package com.winston.boot

import com.reactor.nlp.utilities.IPTools
import com.typesafe.config.ConfigFactory
import com.winston.api.ApiActor
import com.winston.engine.actor.CommandEngineActor
import com.winston.listener.Listener
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster
import akka.kernel.Bootable
import com.winston.nlp.actor.NLPActor
import akka.cluster.routing.{AdaptiveLoadBalancingRouter, ClusterRouterConfig, ClusterRouterSettings}
import akka.routing.RoundRobinRouter
import akka.cluster.ClusterEvent.ClusterDomainEvent
import akka.io.IO
import spray.can.Http
import com.winston.engine.query.actor.CategorizerActor
import com.winston.patterns.pull.FlowControlConfig
import com.winston.patterns.pull.FlowControlFactory
import com.winston.nlp.actor.NLPMaster
import com.winston.patterns.pull.FlowControlArgs
import com.reactor.facebook.FacebookService
import com.reactor.facebook.FacebookFetcher
import com.reactor.facebook.StoryBuilder
import com.reactor.parsletongue.engine.Topic
import com.winston.word2vec.WordVectorAnalyzerActor

class ReqRoomBoot extends Bootable {
  val ip = IPTools.getPrivateIp(); 
  println("IP: " + ip)
  
  val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=2551") 
    .withFallback(ConfigFactory.parseString("akka.cluster.roles = [reducto-frontend]\nakka.remote.netty.tcp.hostname=\""+ip+"\""))
    .withFallback(ConfigFactory.load("reducto"))
    
  implicit val system = ActorSystem("NLPClusterSystem-0-1", config)
  
  Cluster(system) registerOnMemberUp{
  
    val nlpFlowConfig = FlowControlConfig(name="nlpActor", actorType="com.winston.nlp.actor.NLPActor")
    val nlpActor = FlowControlFactory.flowControlledActorForSystem(system, nlpFlowConfig)    
    
    val categorizerConfig = FlowControlConfig(name="categorizerActor", actorType="com.winston.engine.query.actor.CategorizerActor")
    val categorizerActor = FlowControlFactory.flowControlledActorForSystem(system, categorizerConfig) 
    
    val w2vActor = system.actorOf(Props(classOf[WordVectorAnalyzerActor], nlpActor))
    
    val engineActor = system.actorOf(Props(classOf[CommandEngineActor], nlpActor, categorizerActor)
    					.withRouter(ClusterRouterConfig(RoundRobinRouter(),ClusterRouterSettings(
    							totalInstances = 100, maxInstancesPerNode = 1, allowLocalRoutees = true,
    							useRole = Some("reducto-frontend")))),
    							name = "engineActor");
    
    val fbBuilder = system.actorOf(Props(classOf[StoryBuilder]))
    val fbFetch = system.actorOf(Props(classOf[FacebookFetcher], fbBuilder))
    val fbActor = system.actorOf(Props(classOf[FacebookService], fbFetch))
    
	val service = system.actorOf(Props(classOf[ApiActor], engineActor, fbActor, w2vActor).withRouter(	
	  ClusterRouterConfig(AdaptiveLoadBalancingRouter(akka.cluster.routing.MixMetricsSelector), 
	  ClusterRouterSettings(
   	  totalInstances = 100, maxInstancesPerNode = 1,
   	  allowLocalRoutees = true, useRole = Some("reducto-frontend")))),
   	  name = "serviceRouter")
   	
   	IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)
  }
  
  def startup = Cluster(system).subscribe(system.actorOf(Props(classOf[Listener], system), name = "clusterListener"), classOf[ClusterDomainEvent])
  
  def shutdown = system.shutdown
}

object ReqRoomBoot{
  def main(args:Array[String]) = {
    val api = new ReqRoomBoot
    api.startup
  }
}