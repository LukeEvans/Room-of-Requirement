package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.apifacades.winstonapi.WinstonAPI

class EntertainmentType extends QueryType{
	var winstonAPI = new WinstonAPI
	typeString = "entertainment"
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  
	  val comicSet = winstonAPI.comicCall
	  var data = new QueryData
	  
	  data.addSet(comicSet)
	  data
	}
}