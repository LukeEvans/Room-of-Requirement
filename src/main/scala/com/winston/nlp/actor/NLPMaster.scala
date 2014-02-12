package com.winston.nlp.actor

import com.winston.masterworker.Master

import akka.actor.Props
import akka.cluster.routing.ClusterRouterConfig
import akka.cluster.routing.ClusterRouterSettings
import akka.routing.RoundRobinRouter

class NLPMaster(parallel:Int, role:String) extends Master("nlp-master"){
  log.info("NLP Master starting...")
  
  val nlpRouter = context.actorOf(Props(classOf[NLPWorker], self).withRouter(ClusterRouterConfig(RoundRobinRouter(), 
      ClusterRouterSettings(
	  totalInstances = 10, maxInstancesPerNode = parallel,
	  allowLocalRoutees = true, useRole = Some(role)))),
	  name = "nlpRouter")

}