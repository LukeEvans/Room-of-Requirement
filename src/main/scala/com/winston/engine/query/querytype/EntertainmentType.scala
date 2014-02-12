package com.winston.engine.query.querytype

import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import java.util.ArrayList
import scala.collection.JavaConversions._

class EntertainmentType extends QueryType{
	var winstonAPI = new WinstonAPI
	typeString = "entertainment"
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  val queryData = new QueryData
	  
	  val actions = selectActions(query, creds)
	  
	  actions map { fn => queryData.addSet(fn.apply)}

	  queryData
	}
	
	def selectActions(query:String, creds:UserCredentials):ArrayList[()=>ArrayList[Object]] = {
	  val actions = new ArrayList[() =>ArrayList[Object]]
	  
	  if(query.contains("comics"))
	    actions.add(funcOf(winstonAPI.comicCall))
	    
	  actions
	}
}