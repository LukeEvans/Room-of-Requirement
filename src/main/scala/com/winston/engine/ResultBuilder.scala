package com.winston.engine

import com.winston.apifacades.storygraph.GraphResult
import java.util.ArrayList

class ResultBuilder {
	
	def buildResult(resultType:String, graphResult:GraphResult):QueryData = {
	  var data = new QueryData()
	  
	  var wiki = new ArrayList[Object]
	  wiki.add(graphResult.getTopCandidate)
	  data.addSet(wiki)
	  data.addSet(graphResult.getNews)
	  data.addSet(graphResult.getTweets)
	  data.addSet(graphResult.getYoutube)
	  
	  data
	}
}