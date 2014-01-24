package com.winston.messaging

import org.codehaus.jackson.map.ObjectMapper
import spray.http.HttpRequest

class CommandRequest extends Message {
	@transient
	val mapper = new ObjectMapper()
  
	var commandString:String = null
	
	def this(command:String, bool:Boolean){
	  this()
	  commandString = command
	}
	
	def this(request:String){
	  this()
	  var cleanRequest = request.replaceAll("\\r", " ").replaceAll("\\n", " ").trim();
	  val reqJson = mapper.readTree(cleanRequest);	  
	  if(reqJson.has("text"))
	    commandString = reqJson.get("text").asText()
	}
	
	def this(request:HttpRequest){
	  this()
	  commandString = if(request.uri.query.get("text") != None) request.uri.query.get("text").get else null
	}
}