package com.winston.engine.query

import java.util.ArrayList
import com.winston.nlp.NLPWordSet
import com.winston.nlp.NLPSplitter
import com.winston.engine.query.querytype.QueryType
import com.winston.engine.QueryData
import com.winston.messaging.CommandRequest

class EngineQuery {
	var queryString:String = null
	var queryType:String = null
	var query:QueryType = null
	var wordSet:NLPWordSet  = null
	var categorizer = new Categorizer("test")
	var credentials:UserCredentials = null
	var splitter = new NLPSplitter
	splitter.init
	
   def this(queryCommand:CommandRequest){
	  this()
	  this.queryString = queryCommand.commandString 
	 // wordSet = processNLP(queryString)
	  credentials = getCredentials(queryCommand)
	  var categorizer = new Categorizer("test")
	  //query = formulateQuery(wordSet)
	  println(query.typeString)
	  var splitter = new NLPSplitter
	  splitter.init
	}
  	
	// Execute the query and return QueryData
	def execute():QueryData = 
	  query.process(queryString, credentials)

//	private def processNLP(query:String):NLPWordSet = 
//	  splitter.splitProcess(query)
	
//	private def formulateQuery(querySet:NLPWordSet):QueryType = 
//	  categorizer.formulate(querySet)
	  
	private def getCredentials(req:CommandRequest):UserCredentials = {
	  return new UserCredentials(req.udid).setFBToken(req.facebook_token)
			  							.setTwitterToken(req.twitter_token)
			  							.setLoc(req.loc)
			  							.setTzOffset(req.timezone_offset)
			  							.setStocks(req.stocks)
			  							.getName()
			  							
	}
}