package com.winston.engine

import akka.actor.Actor
import akka.actor.ActorRef
import com.winston.messaging.RequestContainer
import com.winston.storygraph.StoryGraphAPI
import com.winston.engine.query.EngineQuery
import com.winston.messaging.DataContainer

class CommandEngineActor extends Actor {
  var storyGraph = new StoryGraphAPI()
  var resultBuilder = new ResultBuilder()
  
  def receive = {
    case request:RequestContainer =>
      processCommand(request.commandRequest.commandString, sender)
  }
  
  def processCommand(command:String, origin:ActorRef){
  	
  	// analyze
  	var query = new EngineQuery()
  	
  	// query storygraph
  	var graphResult = storyGraph.getData(command)
  	
  	// structure response
  	var data = resultBuilder.buildResult(query.queryType, graphResult)
  	
  	origin ! DataContainer(data)
  }

}