package com.winston.engine.query

class EngineQuery {
	var queryString:String = null
	var queryType:String = null
  
	def this(queryString:String, queryType:String){
	  this()
	  this.queryString = queryString 
	  this.queryType = queryType
	}
  
	def execute():Object = {
	  return null
	}
}