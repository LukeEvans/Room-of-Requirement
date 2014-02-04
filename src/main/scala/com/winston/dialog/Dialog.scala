package com.winston.dialog

import java.util.ArrayList
import scala.collection.JavaConversions._
import org.codehaus.jackson.JsonNode

class Dialog {
	var speechList = new ArrayList[String]
	var responseList = new ArrayList[Speech]
	
	def this(json:JsonNode){
		this()
		
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
		if(responseList == null)
			null
    
		responseList.get(0)
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