package com.winston.boot

import akka.actor.ActorLogging
import akka.actor.Actor
import com.winston.patterns.pull.FlowControlConfig
import com.winston.patterns.pull.FlowControlFactory
import com.winston.patterns.pull.FlowControlArgs

case class bootArgs() extends FlowControlArgs

class EngineBootstrap extends Actor with ActorLogging{
 
  def init(){
	val nlpFlowConfig = FlowControlConfig(name="nlpActor", actorType="com.winston.nlp.NLPActor")
    val nlpActor2 = FlowControlFactory.flowControlledActorFor(context, nlpFlowConfig, bootArgs())
  }
  
  override def receive = null

}