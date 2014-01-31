package com.winston.engine.query.querytype

import java.util.ArrayList
import com.winston.engine.query.Word
import com.winston.engine.QueryData
import com.winston.apifacades.storygraph.StoryGraphAPI
import com.winston.engine.ResultBuilder
import com.winston.engine.query.UserCredentials

class SearchType extends QueryType{
	var storyGraph = new StoryGraphAPI()
    var resultBuilder = new ResultBuilder()
  
	override def init(){
	  typeString = "search"
	    
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
		var graphResult = storyGraph.getData(query)  	
		// structure response
		var data = resultBuilder.buildResult(typeString, graphResult)
		data
	}
	
	override def process(query:String, creds:UserCredentials):QueryData =
		process(query)
}