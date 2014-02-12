package com.winston.engine

import akka.actor.Actor
import akka.actor.ActorRef
import com.winston.messaging.RequestContainer
import com.winston.apifacades.storygraph.StoryGraphAPI
import com.winston.engine.query.EngineQuery
import com.winston.messaging.DataContainer
import com.winston.messaging.CommandRequest
import akka.actor.ActorLogging

class CommandEngineActor extends Actor with ActorLogging {
  
  def receive = {
    case request:RequestContainer =>
      processCommand(request.commandRequest, sender)
    case _:Any => println("unknown message received")
  }
  
  def processCommand(command:CommandRequest, origin:ActorRef){  	
  	// analyze
  	var query = new EngineQuery(command)
  	
  	var data = query.execute
  	
  	origin ! DataContainer(data)
  }
}