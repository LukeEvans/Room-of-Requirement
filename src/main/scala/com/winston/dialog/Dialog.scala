package com.winston.dialog

import java.util.ArrayList
import com.mongodb.casbah.commons.MongoDBObject

class Dialog {
	var name:String = null
	var speechList = new ArrayList[String]
	var responseDialogs = new ArrayList[Dialog]
	
	def this(mongoObj:MongoDBObject){
	  this()
	  
	}
	
}