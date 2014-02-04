package com.winston.apifacades.winstonapi

import java.util.ArrayList
import com.winston.utlities.Tools
import scala.collection.JavaConversions._
import com.winston.engine.query.UserCredentials

class WinstonAPI {
	var baseUrl = "http://v036.winstonapi.com"
	  
	def weatherCall(loc:String, timezone:Int):ArrayList[Object] = {
	  var weatherUrl = baseUrl+"/weather?loc="+loc+"&timezone_offset="+Integer.toString(timezone)
	  var response = Tools.fetchURL(weatherUrl);
	  if(!response.has("data"))
	    return null
	  
	  var weatherSet = new ArrayList[Object]
	  for(data <- response.get("data"))
		  weatherSet.add(data)
	  
	  return weatherSet
	}
	
//	def briefingCall(loc:String, timezone:Int, fbToken:String, twitterToken:String, twitterSecret:String):ArrayList[Object] = {
	def briefingCall2(creds:UserCredentials):ArrayList[Object] = {
	  var url = baseUrl + "/brief?"
	  if(creds.udid != null)
	    url += ("UDID="+ creds.udid + "&")
	  if(creds.loc != null)
	    url += ("loc="+ creds.loc + "&")
	  if(creds.facebook_token != null)
	    url += ("facebook_token="+ creds.facebook_token + "&")
	  if(creds.twitter_token != null)
	    url += ("twitter_token="+ creds.twitter_token + "&")
	  if(creds.twitter_secret != null)
	    url += ("twitter_token_secret="+ creds.twitter_secret)	
	  url += ("timezone_offset="+ creds.timezone_offset + "&")
	    
	  var response = Tools.fetchURL(url)
	  if(!response.has("data"))
	    return null
	  
	  var briefSet = new ArrayList[Object]
	  for(data <- response.get("data"))
		  briefSet.add(data)
	  
	  return briefSet
	}
	
	def briefingCall(creds:UserCredentials):ArrayList[ArrayList[Object]] = {
	  var url = baseUrl + "/brief?"
	  if(creds.udid != null)
	    url += ("UDID="+ creds.udid + "&")
	  if(creds.loc != null)
	    url += ("loc="+ creds.loc + "&")
	  if(creds.facebook_token != null)
	    url += ("facebook_token="+ creds.facebook_token + "&")
	  if(creds.twitter_token != null)
	    url += ("twitter_token="+ creds.twitter_token + "&")
	  if(creds.twitter_secret != null)
	    url += ("twitter_token_secret="+ creds.twitter_secret)	
	  url += ("timezone_offset="+ creds.timezone_offset + "&")
	  
	  var response = Tools.fetchURL(url)
	  if(!response.has("data"))
	    return null
	  
	  var briefSet = new ArrayList[ArrayList[Object]]
	  var weatherSet = new ArrayList[Object]
	  var newsSet = new ArrayList[Object]
	  var twitterSet = new ArrayList[Object]
	  var fbSet = new ArrayList[Object]
	  
	  weatherSet = weatherCall(creds.loc, creds.timezone_offset)
	  
	  for(data <- response.get("data")){
	    if(data.has("type")){
	      data.get("type").asText() match{
	        case "News" => newsSet.add(data)
	        case "Facebook" => fbSet.add(data)
	        case "Twitter" => twitterSet.add(data)
	        case a:Any => println("Not added - " + a)
	      }
	    }
	  }
	  
	  if(weatherSet != null && !weatherSet.isEmpty())
	    briefSet.add(weatherSet)
	  if(!newsSet.isEmpty())
	    briefSet.add(newsSet)
	  if(!twitterSet.isEmpty())
	    briefSet.add(twitterSet)
	  if(!fbSet.isEmpty())
	    briefSet.add(fbSet)

	  briefSet
	}
}