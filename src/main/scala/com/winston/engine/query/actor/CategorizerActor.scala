package com.winston.engine.query.actor

import akka.actor.ActorLogging
import akka.actor.Actor
import com.winston.engine.query.Categorizer
import com.winston.messaging.WordSetContainer

class CategorizerActor extends Actor with ActorLogging {
  val categorizer = new Categorizer("test")
  
  override def preStart = println("Categorizer Actor starting...")
  override def postStop = println("Categorizer Actor shutting down..")
  
  override def receive = { 
    case querySet:WordSetContainer => sender ! categorizer.formulate(querySet.wordSet)
    case a:Any => println("Unknown message received of type: " + a.getClass)
  }
  
  
  
}