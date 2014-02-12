package com.winston.engine.query.actor

import akka.actor.ActorLogging
import akka.actor.Actor
import com.winston.engine.query.Categorizer
import com.winston.messaging.WordSetContainer
import com.winston.patterns.pull.FlowControlActor
import com.winston.patterns.pull.FlowControlArgs
import akka.actor.ActorRef

class CategorizerActor(args:FlowControlArgs) extends FlowControlActor(args){
  ready
  val categorizer = new Categorizer("test")
  
  override def preStart = println("Categorizer Actor starting...")
  override def postStop = println("Categorizer Actor shutting down..")
  
  override def receive = { 
    case querySet:WordSetContainer => process(querySet, sender)
    case a:Any => println("Unknown message received of type: " + a.getClass)
  }
  
  def process(querySet:WordSetContainer, origin:ActorRef){
    origin ! categorizer.formulate(querySet.wordSet)
    complete
  }
  
}