package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.engine.ResultBuilder
import com.winston.apifacades.storygraph.StoryGraphAPI

class VideoType extends QueryType{
	var storyGraph = new StoryGraphAPI()
    var resultBuilder = new ResultBuilder()
	typeString = "video"

	override def init(){
  	  
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("videos", "video", 10))
	  wordBank.add(new Word("vids", "vid", 10))
	  wordBank.add(new Word("recordings", "recording", 10))
	  
	  wordBank.add(new Word("find", "find", 3))
	  wordBank.add(new Word("show", "show", 3))
	  wordBank.add(new Word("see", "see", 3))
	  
	  
	  wordBank.add(new Word("get", "get", 3))
	  wordBank.add(new Word("give", "give", 3))
	  wordBank.add(new Word("of", "of", 3))
	  
	  wordBank.add(new Word("watch", "watch", 3))
	  wordBank.add(new Word("play", "play", 3))
	  wordBank.add(new Word("me", "me", 3)) 
	  
	  wordBank.add(new Word("on", "on", 1))
	  wordBank.add(new Word("with", "with", 1))
	} 
	
	override def process(query:String, creds:UserCredentials):QueryData = {
		var graphResult = storyGraph.getData(query)
		// structure response
		var data = new QueryData
		data.addSet(graphResult.getYoutube)
		data
  	}
}