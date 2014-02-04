package com.winston.engine.query.querytype

import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.nlp.NLPWordSet
import com.winston.dialog.DialogDB
import scalaz.std.set
import java.util.ArrayList
import com.winston.cards.dialog.ResponseCard
import com.winston.cards.dialog.DialogCard

class DialogType extends QueryType{
	var dialogDB = new DialogDB
	
	override def init(){
	  
	}
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  var dialog = dialogDB.findDialog(query)
	  
	  var data = new QueryData
	  var dialogSet = new ArrayList[Object]
	  dialogSet.add(new ResponseCard(dialog))
	  data.addSet(dialogSet)
	  
	  data
	}
	
	override def rank(queryWordSet:NLPWordSet):Double = {
	  var total = 0.0

	  if(dialogDB.containsSpeech(queryWordSet.queryString))
	    total = 1000
	  
	  total
	}
}