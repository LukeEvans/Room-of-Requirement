package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.apifacades.winstonapi.WinstonAPI

class StocksType extends QueryType{
	var winstonAPI = new WinstonAPI
	typeString = "stocks"
  
	override def process(query:String, creds:UserCredentials):QueryData = {
	  val stocksSet = winstonAPI.stocksCall(creds)
	  var data = new QueryData 
	  data.addSet(stocksSet)
	}
}