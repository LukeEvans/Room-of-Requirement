package com.winston.engine.query.querytype

import java.util.ArrayList
import scala.collection.JavaConversions._
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.engine.query.Word
import com.winston.apifacades.winstonapi.WinstonAPI

class NearbyType extends QueryType{
  var winstonAPI = new WinstonAPI
  typeString = "nearby"
  
  override def init(){
    wordBank = new ArrayList[Word]
    
    wordBank.add(new Word("nearby", "nearby", 10))
    
    wordBank.add(new Word("close", "close", 5))
    wordBank.add(new Word("near", "near", 5))
    wordBank.add(new Word("around", "around", 5))
    
    wordBank.add(new Word("by", "by", 2))
    wordBank.add(new Word("where", "where", 2))
    wordBank.add(new Word("locate", "locate", 2))
    wordBank.add(new Word("here", "here", 2))
    
    wordBank.add(new Word("give", "give", 0))
    wordBank.add(new Word("me", "me", 0))
    wordBank.add(new Word("show", "show", 0))
    wordBank.add(new Word("find", "find", 0))
  }
  
  override def process(query:String, creds:UserCredentials):QueryData = {
    val newQuery = refineQuery(query)
    val dataSet = winstonAPI.yelpCall(creds, newQuery)
    var queryData = new QueryData
    queryData.addSet(dataSet)
    queryData
  }
  
  def refineQuery(query:String):String = {
    var refinedQuery = query
    
    for(word <- wordBank)
      refinedQuery = refinedQuery.replaceAll(word.string, "")
    
    refinedQuery
  }
  
  // Get the top words
  def extractTop():ArrayList[Word] = {
    null
  }
}