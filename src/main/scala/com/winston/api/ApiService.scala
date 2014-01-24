package com.winston.api

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import akka.actor._
import akka.actor.ActorRef
import akka.actor.Props
import akka.cluster.routing.AdaptiveLoadBalancingRouter
import akka.cluster.routing.ClusterRouterConfig
import akka.cluster.routing.ClusterRouterSettings
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.json._
import spray.http._
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.httpx.unmarshalling._
import spray.json.DefaultJsonProtocol._
import spray.routing._
import spray.util.LoggingContext
import com.fasterxml.jackson.core.JsonParseException
import scala.compat.Platform
import reflect.ClassTag
import akka.pattern.AskTimeoutException
import akka.routing.RoundRobinRouter
import com.winston.throttle.Dispatcher
import com.winston.throttle.TimerBasedThrottler
import com.winston.throttle.Throttler._
import com.winston.messaging.CommandRequest
import com.winston.messaging.RequestContainer
import com.winston.messaging.ResponseContainer
import com.winston.engine.Result
import java.util.ArrayList
import com.winston.messaging.DataContainer

trait ApiService extends HttpService {
  
  implicit val engineActor:ActorRef
  private implicit val timeout = Timeout(5 seconds);
      
  val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      
  val apiRoute =
        path(""){
          get{
        	  //getFromResource("web/index.html") // html index
            complete{
              "Winston Command-Engine API"
            }
          }
        }~
        path("command"){
                get{
                  respondWithMediaType(MediaTypes.`application/json`){
                          entity(as[HttpRequest]){ obj => 
                            val start = Platform.currentTime
                            val response = new Result();
                          	val request = new CommandRequest(obj)
                            complete {
                            	engineActor.ask(RequestContainer(request))(10.seconds).mapTo[DataContainer] map {
                            	  container =>
                            	    response.finish(container.data.data, mapper)
                            	}
                            }
                          }
                  }        
                }~                        
                post{
                  respondWithMediaType(MediaTypes.`application/json`){
                	  		formFields('text){
                	  				text =>{
                	  				val start = Platform.currentTime
                	  				val request = new CommandRequest(text, true)
                	  				complete {
                	  					engineActor.ask(RequestContainer(request))(10.seconds).mapTo[ResponseContainer] map{
                	  						container =>
                	  						container.response
                	  					}
                	  				}
                	  		  }
                	  		}~
                	  		entity(as[String]){ obj => ctx =>
                	  			val request = new CommandRequest(obj)
                	  			complete{
                	  			  engineActor.ask(RequestContainer(request))(10.seconds).mapTo[ResponseContainer] map{
                	  			    container =>
                	  			      container.response
                	  			  }
                	  			}
                	  		}
                    complete{
                      "OK"
                    }
                  }        
                }
        }~        
        path("health"){
                get{
                        complete{"OK."}
                }
                post{
                        complete{"OK."}
                }
        }~
        pathPrefix("css" / Segment) { file =>
          get {
            getFromResource("web/css/" + file)
          }
        }~
        path(RestPath) { path =>
          val resourcePath = "/usr/local/reducto-dist" + "/config/loader/" + path
          getFromFile(resourcePath)
        }
        
//        def initiateRequest(request:ReductoRequest, ctx: RequestContext) {
//            //val dispatchReq = DispatchRequest(RequestContainer(request), ctx, mapper)
//        	//throttler.tell(Queue(dispatchReq), Actor.noSender)
//        }
        
}

class ApiActor(engine:ActorRef) extends Actor with ApiService {
	def actorRefFactory = context
	val engineActor = engine 	
	println("Starting API Service actor...")
  
implicit def ReductoExceptionHandler(implicit log: LoggingContext) =
  ExceptionHandler {
    case e: NoSuchElementException => ctx =>
      println("no element")
      val err = "\n--No Such Element Exception--"
      log.error("{}\n encountered while handling request:\n {}\n\n{}", err, ctx.request,e)
      ctx.complete(BadRequest, "Ensure all required fields are present.")
    
    case e: JsonParseException => ctx =>
      println("json parse")
      val err = "\n--Exception parsing input--"
      log.error("{}\nencountered while handling request:\n {}\n\n{}", err, ctx.request,e)
      ctx.complete(InternalServerError, "Ensure all required fields are present with all Illegal characters properly escaped")
      
    case e: AskTimeoutException => ctx =>
      println("Ask Timeout")
      val err = "\n--Timeout Exception--"
      log.error("{}\nencountered while handling request:\n {}\n\n{}", err, ctx.request,e)
      ctx.complete(RequestTimeout, "Server Timeout")
    
    case e: NullPointerException => ctx => 
      println("Null Pointer")
      val err = "\n--Exception parsing input--"
      log.error("{}\nencountered while handling request:\n {}\n\n{}", err, ctx.request,e)
      ctx.complete(InternalServerError, "Ensure all required fields are present with all Illegal characters properly escaped")
    
    case e: Exception => ctx => 
      e.printStackTrace()
      println("Unknown")
      val err = "\n--Unknon Exception--"
      log.error("{}\nencountered while handling request:\n {}\n\n{}", err, ctx.request,e)
      ctx.complete(InternalServerError, "Internal Server Error")
  }   
  // Route requests to our HttpService
  def receive = runRoute(apiRoute)
}

