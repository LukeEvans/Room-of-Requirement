package com.winston.apifacades.storygraph

import com.winston.utlities.Tools

class StoryGraphAPI {
	val baseUrl = "http://storygraphv2.elasticbeanstalk.com/"
	
	def getData(topic:String):GraphResult ={
		var response = Tools.fetchURL(baseUrl+"metadata?text=" + topic)
//		if(response.has("data")){
//		  var mapper = new ObjectMapper()
//		  var dataNode = response.get("data")
//		  mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES , false);
//		  var nodeString = dataNode.toString()
//		  var result = mapper.readValue(nodeString, classOf[GraphResult])
//		  return result
//		}
		new GraphResult(response)
	}
}