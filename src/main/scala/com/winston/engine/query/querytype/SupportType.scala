package com.winston.engine.query.querytype

import java.util.ArrayList
import com.winston.engine.query.Word
import com.winston.engine.query.UserCredentials
import com.winston.engine.QueryData
import com.winston.cards.ActionCard

class SupportType extends QueryType{
  typeString = "support"
	
	override def process(query:String, creds:UserCredentials):QueryData = {
	  var data = new QueryData
	  var list = new ArrayList[Object]
	  list.add(new ActionCard)
	  data.addSet(list)
	  data
	}
}