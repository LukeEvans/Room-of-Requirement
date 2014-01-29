package com.winston.engine

import akka.actor.Actor
import akka.actor.ActorRef
import com.winston.messaging.RequestContainer
import com.winston.storygraph.StoryGraphAPI
import com.winston.engine.query.EngineQuery
import com.winston.messaging.DataContainer
import com.winston.messaging.CommandRequest

class CommandEngineActor extends Actor {
  var storyGraph = new StoryGraphAPI()
  var resultBuilder = new ResultBuilder()
  
  def receive = {
    case request:RequestContainer =>
      processCommand(request.commandRequest, sender)
  }
  
  def processCommand(command:CommandRequest, origin:ActorRef){  	
  	// analyze
  	var query = new EngineQuery(command.commandString)
  	
  	var data = query.execute
  	
  	origin ! DataContainer(data)
  }
}