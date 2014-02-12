package com.winston.engine.actor

import com.winston.messaging._
import akka.actor._
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.winston.engine.query.UserCredentials
import com.winston.engine.QueryData
import com.winston.utlities.Timer

class CommandEngineActor(nlpMaster:ActorRef, categorizerActor:ActorRef) extends Actor with ActorLogging {
  
  override def preStart = println("Starting Command Engine Actor")
  
  override def receive = {
    case request:RequestContainer => processCommand(request.commandRequest, sender)
    case a:Any => println("Unknown message received of type: " + a.getClass)
  }
  
  def processCommand(command:CommandRequest, origin:ActorRef){
    implicit val timeout = Timeout(10 seconds)
    
    val query = (nlpMaster ? StringContainer(command.commandString)).mapTo[WordSetContainer]

    query map{ result =>{
        val queryType = (categorizerActor ? result).mapTo[QueryTypeContainer]
        
        queryType map{ result => origin ! DataContainer(result.queryType.process(command.commandString, getCredentials(command)))
          
        }
      }
      
    }
  }
  
  private def getCredentials(req:CommandRequest):UserCredentials = 
    return new UserCredentials(req.udid).setFBToken(req.facebook_token)
			  							.setTwitterToken(req.twitter_token)
			  							.setLoc(req.loc)
			  							.setTzOffset(req.timezone_offset)
			  							.setName(req.written_name, req.spoken_name)
}