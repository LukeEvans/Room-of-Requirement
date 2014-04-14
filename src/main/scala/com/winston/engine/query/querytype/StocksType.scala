package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.apifacades.dumbledore.DumbledoreAPI

class StocksType extends QueryType{
	var dumbledore = new DumbledoreAPI
	
	typeString = "stocks"
  
	override def process(query:String, creds:UserCredentials):QueryData = {
	  
	  val stocksSet = dumbledore.stocks(creds)
	  
	  var data = new QueryData 
	  data.addSet(stocksSet)
	}
}