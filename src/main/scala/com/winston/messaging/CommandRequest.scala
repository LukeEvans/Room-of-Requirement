package com.winston.messaging

import org.codehaus.jackson.map.ObjectMapper
import spray.http.HttpRequest

class CommandRequest extends Message {
	@transient
	val mapper = new ObjectMapper()
  
	var commandString:String = null
	var udid:String = null
	var loc:String = null
	var facebook_token:String = null
	var twitter_token:String = null
	var twitter_secret:String = null
	var timezone_offset:Int = 0
	
	def this(command:String, bool:Boolean){
	  this()
	  commandString = command
	}
	
	def this(request:String){
	  this()
	  var cleanRequest = request.replaceAll("\\r", " ").replaceAll("\\n", " ").trim
	  val reqJson = mapper.readTree(cleanRequest);	  
	  if(reqJson.has("text"))
	    commandString = reqJson.get("text").asText
	  if(reqJson.has("loc"))
	    loc = reqJson.get("loc").asText
	  if(reqJson.has("facebook_token"))
	    facebook_token = reqJson.get("facebook_token").asText
	  if(reqJson.has("twitter_token"))
	    twitter_token = reqJson.get("twitter_token").asText
	  if(reqJson.has("twitter_secret"))
	    twitter_secret = reqJson.get("twitter_secret").asText
	  if(reqJson.has("timezone_offset"))
	    timezone_offset = reqJson.get("timezone_offset").asInt
	  if(reqJson.has("udid"))
	    udid = reqJson.get("udid").asText
	}
	
	def this(request:HttpRequest){
	  this()
	  commandString = if(request.uri.query.get("text") != None) request.uri.query.get("text").get else null
	  loc = if(request.uri.query.get("loc") != None) request.uri.query.get("loc").get else null
	  facebook_token = if(request.uri.query.get("facebook_token") != None) request.uri.query.get("facebook_token").get else null
	  twitter_token = if(request.uri.query.get("twitter_token") != None) request.uri.query.get("twitter_token").get else null
	  twitter_secret = if(request.uri.query.get("twitter_secret") != None) request.uri.query.get("twitter_secret").get else null
	  timezone_offset = if(request.uri.query.get("timezone_offset") != None) request.uri.query.get("timezone_offset").get.toInt else 0
	  udid = if(request.uri.query.get("udid") != None) request.uri.query.get("udid").get else null
	}
}