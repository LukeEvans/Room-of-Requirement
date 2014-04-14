package com.winston.engine.query

import com.winston.user.Name
import com.winston.store.MongoStore
import org.codehaus.jackson.map.ObjectMapper
import com.winston.user.Location

class UserCredentials {
  	@transient
	val mapper = new ObjectMapper()
	var loc:String = null
	var location:Location = null
	var facebook_token:String = null
	var twitter_token:String = null
	var twitter_secret:String = null
	var udid:String = null
	var timezone_offset:Int = 0
	var stocks:List[String] = null
	var name:Name = null
	var mongo = new MongoStore("winston-users")
	
	def this(udid:String){
	  this()
	  this.udid = udid
	  mongo = new MongoStore("winston-users")
	}
	
	def setFBToken(facebook_token:String):UserCredentials = {
	  this.facebook_token = facebook_token
	  this
	}
	
	def setTwitterToken(twitter_token:String):UserCredentials = {
	  this.twitter_token = twitter_token
	  this
	}
	
	def setLoc(loc:String):UserCredentials = {
	  if(loc != null && !loc.equalsIgnoreCase("")){
		  this.loc = loc
		  this.location = new Location(loc)
	  }
	  this
	}
	
	def setTzOffset(timezone_offset:Int):UserCredentials = {
	  this.timezone_offset = timezone_offset
	  this
	}
	
	def getName():UserCredentials = {
	  if(udid == null)
	    null
	  var userJson = mongo.findOneSimple("UDID", udid)
	  var cleanJson = userJson.toString().replaceAll("\\r", " ").replaceAll("\\n", " ").trim
	  val reqJson = mapper.readTree(cleanJson);	
	  if(reqJson.has("demographic")){
	    if(reqJson.get("demographic").has("demographic")){
	      if(reqJson.get("demographic").get("demographic").has("data")){
	        name = new Name(reqJson.get("demographic").get("demographic").get("data").get("first_name").asText())
	      }
	    }
	  }
	  this
	}
	
	def setName(written:String, spoken:String):UserCredentials ={
	  if(written != null && spoken != null){
		if(!written.equalsIgnoreCase("") && !spoken.equalsIgnoreCase("")){
		  name = new Name(written, spoken);
	  	}
	  }
	  this
	}
	
	
	def setStocks(stocks:List[String]):UserCredentials = {
	  
	  if(stocks != null && stocks.length > 0)
	    this.stocks = stocks
	  
	  this
	}
}