package com.winston.engine.query.querytype

import com.winston.engine.query.Word
import java.util.ArrayList
import com.winston.engine.QueryData
import com.winston.winstonapi.WinstonAPI
import com.winston.engine.query.UserCredentials

class BriefingType extends QueryType{
	var winstonAPI = new WinstonAPI
	
  	override def init(){
  	  typeString = "brief"
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("briefing", "brief", 10));
  	  wordBank.add(new Word("brief", "brief", 10));
	  wordBank.add(new Word("update", "update", 10));
	  wordBank.add(new Word("report", "report", 10));
	}
  	
  	override def process(query:String):QueryData = {
  	  var data = new QueryData
//  	  data.addSet(winstonAPI.briefingCall("40.0176,-105.2797", 7, "CAAEZCAvKJbJgBAPP3ZBEzr2AWCkZCUIEUEfzuUAY1zwWj4DOCnPwfpTDm7UElajg27cuDvEmkwq0uLgXRKl4zpRc6cvvEBETdkW230ZCcG2Bc1goRrSrnLCcEKVJFoIIonn4oj4ZCE3RvodCmHZAPVB2f1PKsZAzS12d2rkLZBRKoZAQqmfKWiYlzVkOiKXkTDMSNUXlZA2BwZAzAdsfm09r5Yt"))	  
  	  data
  	}
  	
  	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var data = new QueryData
  	  data.addSet(winstonAPI.briefingCall(creds))	  
  	  data
  	}
}