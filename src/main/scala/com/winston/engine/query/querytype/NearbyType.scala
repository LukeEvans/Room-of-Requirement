package com.winston.engine.query.querytype

import java.util.ArrayList

import scala.collection.JavaConversions._

import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.engine.QueryData
import com.winston.engine.query.UserCredentials
import com.winston.engine.query.Word

class NearbyType extends QueryType{
  var winstonAPI = new WinstonAPI
  typeString = "nearby"
  
  override def init(){
    wordBank = new ArrayList[Word]
    
    wordBank.add(new Word("nearby", "nearby", 10))
    
    wordBank.add(new Word("closest", "close", 5))
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
    val yelpSet = winstonAPI.yelpCall(creds, newQuery)
    //val instaSet = winstonAPI.instagramCall(creds, "")
    var queryData = new QueryData
    queryData.addSet(yelpSet)
    //queryData.addSet(instaSet)
    queryData
  }
  
  def refineQuery(query:String):String = {
    var refinedQuery = query.toLowerCase()
    
    for(word <- wordBank)
      refinedQuery = refinedQuery.replaceAll(word.string, "")
    
    if(refinedQuery.charAt(0) == ' ')
      refinedQuery = refinedQuery.substring(1, refinedQuery.size)
      
    refinedQuery
  }
  
  // Get the top words
  def determineActions(query:String, creds:UserCredentials):QueryData = {
    val actionList = new ArrayList[() => ArrayList[Object]]
    	
    if(query.equalsIgnoreCase("ALL")){
      actionList.add(funcOf(winstonAPI.instagramCall(creds, "")))
      actionList.add(funcOf(winstonAPI.yelpCall(creds, "")))
      actionList.add(funcOf(winstonAPI.weatherCall(creds.loc, creds.timezone_offset)))
      // add wiki
    }
	if(query.contains("photos"))
	  actionList.add(funcOf(winstonAPI.instagramCall(creds, "")))
	else
	  actionList.add(funcOf(winstonAPI.yelpCall(creds, query)))

    val queryData = new QueryData
    
    actionList map { fn => queryData.addSet(fn.apply)} 

    queryData  
  }
 
}