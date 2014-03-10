package com.winston.apifacades.storygraph

import com.winston.utlities.Tools
import com.fasterxml.jackson.databind.JsonNode

class StoryGraphAPI {
	val baseUrl = "http://accio.winstonapi.com:8080/"
	
	def getData(topic:String, fb_token:Option[String]):GraphResult ={
	  var response:JsonNode = null
	  fb_token match{
	    case Some(token) => response = Tools.fetchURL(baseUrl + "metadata?text=" + topic + "&alt=true&facebook_token=" + token)
	    case None => response = Tools.fetchURL(baseUrl + "metadata?text=" + topic + "&alt=true")
	  }
	  new GraphResult(response)
	}
}