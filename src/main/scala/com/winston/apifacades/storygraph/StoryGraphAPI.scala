package com.winston.apifacades.storygraph

import com.winston.utlities.Tools

class StoryGraphAPI {
	val baseUrl = "http://accio.winstonapi.com/"
	
	def getData(topic:String):GraphResult ={
		var response = Tools.fetchURL(baseUrl+"metadata?text=" + topic)
		new GraphResult(response)
	}
}