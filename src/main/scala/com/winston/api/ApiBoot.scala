package com.winston.api

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import com.reactor.nlp.utilities.IPTools
import com.typesafe.config.ConfigFactory
import akka.cluster.Cluster
import akka.cluster.routing.{ClusterRouterConfig, AdaptiveLoadBalancingRouter, ClusterRouterSettings}
import akka.kernel.Bootable
import akka.routing.RoundRobinRouter
import akka.cluster.ClusterEvent.ClusterDomainEvent
import com.winston.listener.Listener
import com.winston.engine.CommandEngineActor

//class ApiBoot(args: Array[String]) extends Bootable {
class ApiBoot extends Bootable {

	val ip = IPTools.getPrivateIp(); 
	println("IP: " + ip)
	
	val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=2551") 
      .withFallback(ConfigFactory.parseString("akka.cluster.roles = [reducto-frontend]\nakka.remote.netty.tcp.hostname=\""+ip+"\"")).withFallback(ConfigFactory.load("reducto"))
      
    implicit val system = ActorSystem("NLPClusterSystem-0-1", config)
        
    //registerOnUp
    Cluster(system) registerOnMemberUp {
	  
		val engineActor = system.actorOf(Props(classOf[CommandEngineActor]).withRouter(ClusterRouterConfig(RoundRobinRouter(),
		    ClusterRouterSettings(
		        totalInstances = 100, maxInstancesPerNode = 1, allowLocalRoutees = true, useRole = Some("reducto-frontend")))),
		        name = "engineActor");
		
   		val service = system.actorOf(Props(classOf[ApiActor], engineActor).withRouter(	
    	  ClusterRouterConfig(AdaptiveLoadBalancingRouter(akka.cluster.routing.MixMetricsSelector), 
    	  ClusterRouterSettings(
    	  totalInstances = 100, maxInstancesPerNode = 1,
    	  allowLocalRoutees = true, useRole = Some("reducto-frontend")))),
    	  name = "serviceRouter")
    		 
       IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)
    }
  

     def startup(){
         val clusterListener = system.actorOf(Props(classOf[Listener], system),
             name = "clusterListener")
         Cluster(system).subscribe(clusterListener, classOf[ClusterDomainEvent])
    }

	def shutdown(){
		system.shutdown()
	}
}

object ApiApp {
   def main(args: Array[String]) = {
     val api = new ApiBoot
     api.startup
   }
}
