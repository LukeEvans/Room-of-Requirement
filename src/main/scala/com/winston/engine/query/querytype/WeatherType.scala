package com.winston.engine.query.querytype

import java.util.ArrayList
import com.winston.engine.query.Word
import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials

class WeatherType extends QueryType {
	var winstonAPI = new WinstonAPI
	
  	override def init(){
  	  typeString = "weather"
  	  
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("weather", "weather", 10))
	  wordBank.add(new Word("temperature", "temperature", 10))
	  wordBank.add(new Word("forecast", "forecast", 10))

	  
	  wordBank.add(new Word("hot", "hot", 7))
	  wordBank.add(new Word("warm", "warm", 7))
	  wordBank.add(new Word("cold", "cold", 7))
	  
	  
	  //check lemmas of words like rainy, foggy, etc
	  wordBank.add(new Word("rainy", "rain", 5))
	  wordBank.add(new Word("snowy", "snow", 5))
	  wordBank.add(new Word("sunny", "sun", 5))
	  wordBank.add(new Word("foggy", "fog", 5))
	  
	  wordBank.add(new Word("will", "will", 1))
	  wordBank.add(new Word("what", "what", 1))
	  wordBank.add(new Word("is", "is", 1))
	}
  	
  	override def process(query:String):QueryData = {
  	  var data = new QueryData
  	  data.addSet(winstonAPI.weatherCall("40.0176,-105.2797", 7))	  
  	  data
  	}
  	
   	override def process(query:String, creds:UserCredentials):QueryData = {
  	  var data = new QueryData
  	  data.addSet(winstonAPI.weatherCall(creds.loc, creds.timezone_offset))	  
  	  data
  	}
}