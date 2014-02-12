package com.winston.nlp.actor

import akka.actor.ActorRef
import com.winston.masterworker.Worker
import akka.actor.Props

class NLPWorker(master:ActorRef) extends Worker(master) {
  implicit val ec = context.dispatcher
  log.info("NLP Worker starting...")
  
  val nlpActor = context.actorOf(Props(classOf[NLPActor], self), "NLPActor")
  
  def doWork(workSender:ActorRef, msg:Any) = nlpActor.tell(msg, workSender)
}