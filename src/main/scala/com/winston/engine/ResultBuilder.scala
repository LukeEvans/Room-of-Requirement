package com.winston.engine

import com.winston.storygraph.GraphResult
import java.util.ArrayList

class ResultBuilder {
	
	def buildResult(resultType:String, graphResult:GraphResult):QueryData = {
	  var data = new QueryData()
	  
	  var wiki = new ArrayList[Object]
	  wiki.add(graphResult.getTopCandidate)
	  data.addSet(wiki)
	  
	  var news = new ArrayList[Object]
	  news.add(graphResult.getNews)
	  data.addSet(news)
	  
	  var tweets = new ArrayList[Object]
	  tweets.add(graphResult.getTweets)
	  data.addSet(tweets)
	  
	  return data
	}
}