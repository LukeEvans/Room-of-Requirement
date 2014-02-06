package com.winston.dialog

import java.util.ArrayList
import scala.collection.JavaConversions._
import org.codehaus.jackson.JsonNode
import com.winston.utlities.Tools

class Dialog {
	var speechList = new ArrayList[String]
	var responseList = new ArrayList[Speech]
	var action_type:String = null
	var action_query:String = null
	
	def this(json:JsonNode){
		this()
		if(json.has("action")){
		  action_type = json.get("action").get("type").asText()
		  action_query = json.get("action").get("query").asText()
		}
		if(json.has("speech"))
		  speechList = getSpeechList(json.get("speech"))
		if(json.has("response"))
		  responseList = getResponseList(json.get("response"))
	}
	
	def containsSpeech(speechString:String):Boolean = {
		for(speechObj <- speechList){
			if(speechString.equalsIgnoreCase(speechObj))
				return true
    	}
    	false
	}
	
	def getResponse():Speech = {
		if(responseList == null || responseList.isEmpty())
			return null
		var rando = Tools.randomInt(0, responseList.size - 1)
		responseList.get(rando)
	}
	
	def getSpeechList(objList:JsonNode):ArrayList[String] = {
	  var list = new ArrayList[String]
	  for(obj <- objList){
	    list.add(obj.asText)
	  }
	  list
	}
	
	def getResponseList(objList:JsonNode):ArrayList[Speech] = {
	  var list = new ArrayList[Speech]
	  for(obj <-objList)
	    list.add(new Speech(obj.get("text").asText(), obj.get("speech").asText()))
	    
	  list  
	}
}