package com.winston.engine.query

import java.util.ArrayList
import java.util.HashMap

import scala.collection.JavaConversions._

import com.winston.engine.query.querytype._
import com.winston.nlp.NLPWordSet

class Categorizer {
	var types:ArrayList[QueryType] = null
	
	def this(string:String) = {
	  this()
	  types = new ArrayList[QueryType]
	  var brief = new BriefingType
	  brief.init
	  types.add(brief)
	  var weather = new WeatherType
	  weather.init
	  types.add(weather)
	  var search = new SearchType
	  search.init
	  types.add(search)
	}
	
	//Figure out QueryType based of wordset
	def formulate(set:NLPWordSet):QueryType = {
	  
	  var typeMap = new HashMap[String, Double]
	  for(queryType <- types)
	    typeMap.put(queryType.typeString, queryType.rank(set))
	  	  
	  getTypeFromList(getTopRankedTypeString(typeMap))
	}
	
	def getTopRankedTypeString(map:HashMap[String, Double]):String = {
	  
	 var it = map.entrySet().iterator
	
	 var topValue = ("nada", 0.0)
	 while(it.hasNext){
	   var pairs = it.next()
	   
	   if(topValue._2 < pairs.getValue())
	     topValue = (pairs.getKey(), pairs.getValue())   
	 }
	 
	 topValue._1
	}
	
	def getTypeFromList(queryTypeString:String):QueryType ={
	  for(qType <- types){
	    if(qType.typeString.equalsIgnoreCase(queryTypeString))
	      return qType
	  }
	  return null
	}
}