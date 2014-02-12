package com.winston.masterworker

import akka.actor.Terminated
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.cluster.routing.ClusterRouterConfig
import akka.routing.RoundRobinRouter
import akka.cluster.routing.ClusterRouterSettings
import scala.collection.mutable.{Map, Queue}
import com.winston.masterworker.MasterWorkerProtocol._
import com.timgroup.statsd.StatsDClient
import com.timgroup.statsd.NonBlockingStatsDClient
import scala.concurrent.duration._
import com.winston.monitoring.MonitoredActor

class Master(serviceName:String) extends Actor with ActorLogging {

  implicit val ec = context.dispatcher
  
//  val cancellable =
//  context.system.scheduler.schedule(5 seconds,
//    50 milliseconds,
//    self,
//    GetStats)

  val workers = Map.empty[ActorRef, Option[Tuple2[ActorRef, Any]]]

  val workQ = Queue.empty[Tuple2[ActorRef, Any]]

  var workerCreatedMessages = 0;
  
  def notifyWorkers(): Unit = {
    if (!workQ.isEmpty) {
      workers.foreach { 
        case (worker, m) if (m.isEmpty) => worker ! WorkIsReady
        case _ =>
      }
    }
  }
    
  def receive = {
    case WorkerCreated(worker) =>
      log.info("Worker created: {}", worker)
      context.watch(worker)
      workers += (worker -> None)
      notifyWorkers()
      
    case WorkerRequestsWork(worker) =>  
      if (workers.contains(worker)) {
        if (workQ.isEmpty)
          worker ! NoWorkToBeDone
        else if (workers(worker) == None) {
          val (workSender, work) = workQ.dequeue()
          workers += (worker -> Some(workSender -> work))

          worker.tell(WorkToBeDone(work), workSender)
        }
      }

    case WorkIsDone(worker) =>
      if (!workers.contains(worker))
        log.error("Blurgh! {} said it's done work but we didn't know about him", worker)
      else
        workers += (worker -> None)
      
    case Terminated(worker) =>
      println("Master receive")
      if (workers.contains(worker) && workers(worker) != None) {
        log.error("Blurgh! {} died while processing {}", worker, workers(worker))

        val (workSender, work) = workers(worker).get
        self.tell(work, workSender)
      }
      workers -= worker
    
    case work =>
      workQ.enqueue(sender -> work)
      notifyWorkers()
  }
}