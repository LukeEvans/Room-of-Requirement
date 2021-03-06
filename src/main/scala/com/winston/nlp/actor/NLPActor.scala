package com.winston.nlp.actor

import akka.actor.ActorLogging
import akka.actor.Actor
import com.winston.messaging.StringContainer
import akka.actor.actorRef2Scala
import com.winston.nlp.NLPSplitter
import akka.actor.ActorRef
import com.winston.masterworker.MasterWorkerProtocol._
import com.winston.patterns.pull.FlowControlActor
import com.winston.patterns.pull.FlowControlArgs

class NLPActor(args:FlowControlArgs) extends FlowControlActor(args){
	
    ready
  
	var splitter = new NLPSplitter
	splitter.init
	
	override def preStart() = println("Creating NLPActor")
	
	override def postStop() = println("Stopping NLPActor")
	
	override def receive = {
	  case StringContainer(string) => process(string, sender)
	  case a:Any => println("unkown message received nlp from: " +a.toString)
	}
	
	def process(queryString:String, origin:ActorRef) ={
	  println("nlp received process")
	  origin.tell(splitter.splitProcess(queryString), origin)
	  complete
	}
}