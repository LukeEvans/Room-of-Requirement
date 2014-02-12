package com.winston.engine

import java.util.ArrayList

class QueryData {
	var data = new ArrayList[ArrayList[Object]]
	
	def this(data:ArrayList[ArrayList[Object]]){
	  this()
	  this.data = data
	}
	
	def addSet(set:ArrayList[Object]):QueryData = { data.add(set); this}
	
	def addSetToFront(set:ArrayList[Object]) = data.add(0, set)
}