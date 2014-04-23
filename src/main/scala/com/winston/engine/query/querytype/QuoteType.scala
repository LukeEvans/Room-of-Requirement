package com.winston.engine.query.querytype

import com.winston.apifacades.dumbledore.DumbledoreAPI
import com.winston.engine.query.UserCredentials
import com.winston.engine.QueryData
import java.util.ArrayList
import com.winston.engine.query.Word

class QuoteType extends QueryType{
	var dumbledore = new DumbledoreAPI
	
	typeString = "quote"
	  
	override def init(){
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("Quote", "quote", 20));
	}
  
	override def process(query:String, creds:UserCredentials):QueryData = {
	  
	  val quoteSet = dumbledore.quote
	  
	  var data = new QueryData 
	  data.addSet(quoteSet)
	}
}