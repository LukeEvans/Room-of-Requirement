package com.winston.apifacades.winstonapi

import java.util.ArrayList

import scala.collection.JavaConversions._

import com.fasterxml.jackson.databind.JsonNode
import com.winston.engine.query.UserCredentials
import com.winston.utlities.Tools

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
	  weatherSet = weatherCall(creds.loc, creds.timezone_offset)
	  if(weatherSet != null && !weatherSet.isEmpty())
	    briefSet.add(weatherSet)
	  
	  for(data <- response.get("data")){
	    if(data.has("type")){
	      data.get("type").asText() match{
	        case "News" | "Facebook" | "Twitter" => {
	          var newSet = new ArrayList[Object]
	          newSet.add(data)
	          briefSet.add(newSet)
	        }
	        case a:Any => println("Not added - " + a)
	      }
	    }
	  }
	  briefSet
	}
	
	def yelpCall(creds:UserCredentials, typeString:String):ArrayList[Object] = {
		val yelpUrl = baseUrl+"/yelp?"+ "lat="+ creds.location.lat.toString()+"&long="+ creds.location.long.toString + "&type=" +typeString
		val response = Tools.fetchURL(yelpUrl)

		response.get("data") match{
		  case dataNode:JsonNode =>{
		    var dataList = new ArrayList[Object]
		    for(data <- dataNode)
		      dataList.add(data)	      
		    dataList
		  }
		}
	}
	
	def instagramCall(creds:UserCredentials, typeString:String):ArrayList[Object] = {
		val instagramUrl = baseUrl+"/instagram/location?" + 
				"lat="+ creds.location.lat.toString() +
				"&long="+ creds.location.long.toString 

		val response = Tools.fetchURL(instagramUrl)

		response.get("data") match{
		  case dataNode:JsonNode =>{
		    var dataList = new ArrayList[Object]
		    for(data <- dataNode)
		      dataList.add(data)	      
		    dataList
		  }
		}
	}
	
	def comicCall():ArrayList[Object] = {
	  val comicURL = baseUrl+"/comics/random/"
	  val response = Tools.fetchURL(comicURL)
	  
	  response.get("data") match{
	    case dataNode:JsonNode =>{
	      var dataList = new ArrayList[Object]
	      for(data <-dataNode)
	        dataList.add(data)
	      dataList
	    }
	  }
	}
	
	def stocksCall(creds:UserCredentials):ArrayList[Object] = {
	  val stocksURL = baseUrl + "/stocks"
	  val response = Tools.fetchURL(stocksURL)
	  
	  response.get("data") match{
	    case dataNode:JsonNode =>{
	      var dataList = new ArrayList[Object]
	      for(data <-dataNode)
	        dataList.add(data)
	      dataList
	    }
	  }
 	}
}