package com.winston.boot

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props
import com.winston.engine.CommandEngineActor

class ReqRoomBoot extends Actor with ActorLogging {
  val commandEngine = context.actorOf(Props(classOf[CommandEngineActor]))
  def receive ={
    null
  }
}