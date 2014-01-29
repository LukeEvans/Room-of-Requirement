package com.winston.engine.query

class UserCredentials {
	var loc:String = null
	var facebook_token:String = null
	var twitter_token:String = null
	var twitter_secret:String = null
	var udid:String = null
	var timezone_offset:Int = 0
	
	def this(udid:String){
	  this()
	  this.udid = udid
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
	  this.loc = loc
	  this
	}
	
	def setTzOffset(timezone_offset:Int):UserCredentials = {
	  this.timezone_offset = timezone_offset
	  this
	}
}