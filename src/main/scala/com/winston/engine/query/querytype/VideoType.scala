package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials

class VideoType extends QueryType{

	override def init(){
  	  typeString = "video"
  	  
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("videos", "video", 10))
	  wordBank.add(new Word("vids", "vid", 10))
	  wordBank.add(new Word("recordings", "recording", 10))
	  
	  wordBank.add(new Word("find", "find", 5))
	  wordBank.add(new Word("show", "show", 5))
	  wordBank.add(new Word("see", "see", 5))
	  
	  
	  wordBank.add(new Word("get", "get", 5))
	  wordBank.add(new Word("give", "give", 5))
	  wordBank.add(new Word("of", "of", 5))
	  
	  wordBank.add(new Word("watch", "watch", 3))
	  wordBank.add(new Word("play", "play", 3))
	  wordBank.add(new Word("me", "me", 3)) 
	  
	  wordBank.add(new Word("on", "on", 1))
	  wordBank.add(new Word("with", "with", 1))
	} 
	
	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var data = new QueryData
  	  var set = new ArrayList[Object]
  	  set.add("video query")
  	  data.addSet(set)	  
  	  data
  	}
}