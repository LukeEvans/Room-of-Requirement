package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.engine.query.UserCredentials

class BriefingType extends QueryType{
	var winstonAPI = new WinstonAPI
	typeString = "brief"
	  
  	override def init(){
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("briefing", "brief", 10));
	  wordBank.add(new Word("update", "update", 10));
	  wordBank.add(new Word("report", "report", 10));
	}
  	
  	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var data = new QueryData 
  	  data.data = winstonAPI.briefingCall(creds)
  	  data
  	}
}