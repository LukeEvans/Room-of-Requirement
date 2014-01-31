package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials

class PhotoType extends QueryType {

  	override def init(){
  	  typeString = "photo"
  	  
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("photos", "photo", 10))
	  wordBank.add(new Word("photographs", "photograph", 10))
	  wordBank.add(new Word("pictures", "picture", 10))
	  wordBank.add(new Word("pics", "pic", 10))

	  
	  wordBank.add(new Word("find", "find", 7))
	  wordBank.add(new Word("show", "show", 7))
	  wordBank.add(new Word("see", "see", 7))
	  
	  wordBank.add(new Word("get", "get", 5))
	  wordBank.add(new Word("give", "give", 5))
	  wordBank.add(new Word("of", "of", 5))
	  
	  wordBank.add(new Word("me", "me", 3)) 
	  wordBank.add(new Word("display", "display", 3)) 
	  
	  wordBank.add(new Word("on", "on", 1))
	  wordBank.add(new Word("with", "with", 1))
	}
  
  	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var data = new QueryData
  	  var set = new ArrayList[Object]
  	  set.add("photo query")
  	  data.addSet(set)	  
  	  data
  	}
}