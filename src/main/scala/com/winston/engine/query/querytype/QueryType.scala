package com.winston.engine.query.querytype

import java.util.ArrayList
import scala.collection.JavaConversions._
import com.winston.nlp.{NLPWordSet}
import com.winston.engine.query.Word
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials

class QueryType {
	var wordBank:ArrayList[Word] = new ArrayList[Word]
	var typeString:String = ""
	  
	def init(){
	  //initialize wordBank 
	}
	
	def rank(queryWordSet:NLPWordSet):Double = {
	  var total = 0.0
	  for(word <- wordBank){
		  if(queryWordSet.setContains(word))
			  total += word.weight
	  }
	  total
	}
	
	def process(query:String):QueryData = {
	  return new QueryData()
	}
	
	def process(query:String, creds:UserCredentials):QueryData = {
	  return new QueryData()
	}
}