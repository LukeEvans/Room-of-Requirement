package com.winston.engine.query.querytype

import java.util.ArrayList
import com.winston.engine.query.Word
import com.winston.engine.QueryData
import com.winston.apifacades.storygraph.StoryGraphAPI
import com.winston.engine.ResultBuilder
import com.winston.engine.query.UserCredentials
import com.winston.apifacades.storygraph.GraphResult

class SearchType extends QueryType{
	var storyGraph = new StoryGraphAPI()
    var resultBuilder = new ResultBuilder()
	typeString = "search"
  
	override def init(){
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("news", "news", 10));
	  
	  wordBank.add(new Word("about", "about", 7));
	  wordBank.add(new Word("on", "on", 7));
	  wordBank.add(new Word("happening", "happen", 7));
	  wordBank.add(new Word("going", "going", 7));
	  wordBank.add(new Word("what", "what", 7));
	  
	  wordBank.add(new Word("is", "is", 4));
	  wordBank.add(new Word("up", "up", 4));
	  wordBank.add(new Word("search", "search", 4));
	  wordBank.add(new Word("find", "find", 4));
	}
	
	def process(query:String):QueryData = {
		var graphResult = storyGraph.getData(query, None)  	
		// structure response
		var data = resultBuilder.buildResult(typeString, graphResult)
		data
	}
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  var graphResult:GraphResult = null
	  if(creds.facebook_token == null || creds.facebook_token.equalsIgnoreCase(""))
	  	graphResult = storyGraph.getData(query, None)
	  else
	    graphResult = storyGraph.getData(query, Some(creds.facebook_token))
		// structure response
	  var data = resultBuilder.buildResult(typeString, graphResult)
      data
	}

}