package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials

class DialogType extends QueryType{
	override def init(){
	  
	}
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  /* Speech
	   * Text 
	   * type = "dialog"
	   * 
	   */

			
	  new QueryData
	}
}