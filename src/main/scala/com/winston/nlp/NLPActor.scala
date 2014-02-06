package com.winston.nlp

import akka.actor.ActorLogging
import akka.actor.Actor
import scalaz.std.string
import com.winston.messaging.StringContainer

class NLPActor extends Actor with ActorLogging {
	var splitter = new NLPSplitter
	
	override def preStart() = println("Creating NLPActor")
	
	override def postStop() = println("Stopping NLPActor")
	
	override def receive = {
	  case StringContainer(string) => sender ! splitter.splitProcess(string)
	  case _:Any => println("unkown message received")
	}
}