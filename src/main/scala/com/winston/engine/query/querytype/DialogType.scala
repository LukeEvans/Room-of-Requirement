package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.nlp.NLPWordSet
import com.winston.dialog.DialogDB
import scalaz.std.set
import java.util.ArrayList
import com.winston.cards.dialog.ResponseCard
import com.winston.cards.dialog.DialogCard
import com.winston.engine.query.Categorizer

class DialogType extends QueryType{
	var dialogDB = new DialogDB
	var categorizer:Categorizer = null
	typeString = "dialog"
	
	override def init(){
	  categorizer = new Categorizer
	}
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  var dialog = dialogDB.findDialog(query)
	  var data:QueryData = new QueryData
	  var dialogSet = new ArrayList[Object]
	  
	  if(dialog.action_type != null)
	    data = processAction(dialog.action_type, dialog.action_query, creds)
	  
	  if(dialog.responseList != null && !dialog.responseList.isEmpty()){
		  dialogSet.add(new ResponseCard(dialog, creds.name))
		  data.addSetToFront(dialogSet)
	  }
		  
	  data
	}
	
	override def rank(queryWordSet:NLPWordSet):Double = {
	  var total = 0.0

	  if(dialogDB.containsSpeech(queryWordSet.queryString))
	    total = 1000
	  
	  total
	}
	
	def processAction(actionType:String, actionQuery:String, creds:UserCredentials):QueryData = {
	  var query = categorizer.getTypeFromList(actionType)
	  query.process(actionQuery, creds)
	}
	
}