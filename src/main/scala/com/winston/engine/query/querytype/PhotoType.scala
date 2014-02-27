package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.apifacades.winstonapi.WinstonAPI

class PhotoType extends QueryType {
    val winstonAPI = new WinstonAPI
	typeString = "photo"

  	override def init(){
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("photos", "photo", 10))
	  wordBank.add(new Word("photographs", "photograph", 10))
	  wordBank.add(new Word("pictures", "picture", 10))
	  wordBank.add(new Word("pics", "pic", 10))

	  
	  wordBank.add(new Word("find", "find", 7))
	  wordBank.add(new Word("show", "show", 7))
	  wordBank.add(new Word("see", "see", 7))
	  
	  wordBank.add(new Word("get", "get", 3))
	  wordBank.add(new Word("give", "give", 3))
	  wordBank.add(new Word("of", "of", 3))
	  
	  wordBank.add(new Word("me", "me", 3)) 
	  wordBank.add(new Word("display", "display", 3)) 
	  
	  wordBank.add(new Word("on", "on", 1))
	  wordBank.add(new Word("with", "with", 1))
	}
  
  	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var set = new ArrayList[Object]
  	  val instaSet = winstonAPI.instagramCall(creds, "")
  	  val queryData = new QueryData
  	  queryData.addSet(instaSet)
  	  queryData
  	}
}