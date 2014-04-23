package com.winston.api

import scala.compat.Platform
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.winston.engine.Result
import com.winston.messaging.CommandRequest
import com.winston.messaging.DataContainer
import com.winston.messaging.RequestContainer
import com.winston.store.MongoStore
import akka.actor.Actor
import akka.actor.ActorRef
import akka.pattern.AskTimeoutException
import akka.pattern.ask
import akka.util.Timeout
import akka.util.Timeout.durationToTimeout
import spray.http.HttpRequest
import spray.http.MediaTypes
import spray.http.StatusCodes.BadRequest
import spray.http.StatusCodes.InternalServerError
import spray.http.StatusCodes.RequestTimeout
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.ExceptionHandler
import spray.routing.HttpService
import spray.routing.directives.FieldDefMagnet.apply
import spray.util.LoggingContext
import com.winston.dialog.DialogDB
import com.winston.engine.query.querytype.NearbyType
import com.winston.apifacades.winstonapi.WinstonAPI
import com.reactor.facebook.messages.FacebookRequest
import com.reactor.facebook.messages.FBRequestContainer
import com.reactor.facebook.messages.ListContainer
import com.reactor.facebook.messages.FBResult
import com.winston.word2vec.Word2Vec
import com.winston.word2vec.Word2VecQueryAnalyzer
import com.winston.messaging.StringContainer

trait ApiService extends HttpService {
  
  implicit val engineActor:ActorRef
  implicit val fbActor:ActorRef
  implicit val w2vActor:ActorRef
  
  private implicit val timeout = Timeout(5 seconds);
      
  val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      
  val model = new Word2Vec()

      
  val apiRoute =
        path(""){
          get{
        	getFromResource("web/index.html") // html index

          }
        }~
        path("word2vec"){
          get{
            respondWithMediaType(MediaTypes.`application/json`){
              entity(as[HttpRequest]){ request => 

                val string = if(request.uri.query.get("text") != None) request.uri.query.get("text").get else null
                
                complete {
                  
                  if(string != null)
                	  w2vActor.tell(string, w2vActor)
              
                  "complete"
                }
           	  }
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
                	  				val response = new Result();
                	  				val request = new CommandRequest(text, true)
                	  				complete {
                	  					engineActor.ask(RequestContainer(request))(10.seconds).mapTo[DataContainer] map{
                	  						container =>
                	  						response.finish(container.data.data, mapper)
                	  					}
                	  				}
                	  		  }
                	  		}~
                	  		entity(as[String]){ obj =>
                	  		  	val response = new Result()
                	  			val request = new CommandRequest(obj)
                	  			complete{               	  			  
                	  			  engineActor.ask(RequestContainer(request))(10.seconds).mapTo[DataContainer] map{
                	  			    container =>
                	  			      response.finish(container.data.data, mapper)
                	  			  }
                	  			}
                	  		}
                  }        
                }
        }~
        path("fb"){
          post{
            respondWithMediaType(MediaTypes.`application/json`){
              entity(as[String]){
                obj =>
                  val response = new FBResult()
                  val request = new FacebookRequest(obj)
                  complete{
                    fbActor.ask(FBRequestContainer(request))(20.seconds).mapTo[ListContainer] map{
                      container =>
                        response.finish(container.list, mapper)
                    }
                  }
              }
            }
          }
        }~
        path("health"){
        	complete{"OK."}
        }~
        pathPrefix("web" / "css" / Segment) { file =>
          get {
            getFromResource("web/css/" + file)
          }
        }~
        pathPrefix("web" / Segment){
          file =>
            get{
              getFromResource("web/" + file)
            }
        }
        path(RestPath) { path =>
          val resourcePath = "/usr/local/reducto-dist" + "/config/loader/" + path
          getFromFile(resourcePath)
        }
}

class ApiActor(engine:ActorRef, fb:ActorRef, w2v:ActorRef) extends Actor with ApiService {
	def actorRefFactory = context
	val engineActor = engine
	val fbActor = fb
	val w2vActor = w2v
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

