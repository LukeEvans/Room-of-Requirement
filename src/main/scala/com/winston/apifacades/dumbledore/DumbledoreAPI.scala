package com.winston.apifacades.dumbledore

import com.winston.engine.query.UserCredentials
import java.util.ArrayList
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import com.winston.utlities.Tools
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature

class DumbledoreAPI {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  
  val baseURL = "http://ec2-107-22-62-120.compute-1.amazonaws.com:8080/"
    
  
  /** Stocks 
   */
  def stocks(creds:UserCredentials):ArrayList[Object] = {
    
    var request = new Request()
    
    request.addNotificationRequest(new SetRequest("stocks", 0, creds.stocks))
    
    var json = mapper.writeValueAsString(request)
    
    val response = Tools.postJsonString(baseURL + "primetime", json)
    
    var cleanRequest = response.replaceAll("\\r", " ").replaceAll("\\n", " ").trim
    val reqJson = mapper.readTree(cleanRequest);

    if(reqJson.has("data")){
      
      val data = reqJson.get("data")
      
      if(data.size() > 0){
        
        val stocksNode = data.get(0)
        
        val array = new ArrayList[Object]()
        
        stocksNode.get("set_data").foreach( node => array.add(node))
        
        return array
      }
    } 
    
    null
  }
  
  
  /** Quote
   */
  def quote():ArrayList[Object] = {
    
    var request = new Request()
    request.addEntertainmentRequest(new SetRequest("quote", 0, null))
    
    var json = mapper.writeValueAsString(request)
    
    val response = Tools.postJsonString(baseURL + "primetime", json)
    
    var cleanRequest = response.replaceAll("\\r", " ").replaceAll("\\n", " ").trim
    val reqJson = mapper.readTree(cleanRequest);
    
        if(reqJson.has("data")){
      
      val data = reqJson.get("data")
      
      if(data.size() > 0){
        
        val stocksNode = data.get(0)
        
        val array = new ArrayList[Object]()
        
        stocksNode.get("set_data").foreach( node => array.add(node))
        
        return array
      }
    } 
    
    null
    
  }
  
  
  /** Donation
   */
  def donation():ArrayList[Object] = {
    
    var request = new Request()
    request.addNotificationRequest(new SetRequest("donations", 0, null))
    
    var json = mapper.writeValueAsString(request)
    
    val response = Tools.postJsonString(baseURL + "primetime", json)
    
    var cleanRequest = response.replaceAll("\\r", " ").replaceAll("\\n", " ").trim
    val reqJson = mapper.readTree(cleanRequest);
    
    if(reqJson.has("data")){
      
      val data = reqJson.get("data")
      
      if(data.size() > 0){
        
        val stocksNode = data.get(0)
        
        val array = new ArrayList[Object]()
        
        stocksNode.get("set_data").foreach( node => array.add(node))
        
        return array
      }
    } 
    
    null
    
  }
}


class Request{
  
  var notifications = ListBuffer[SetRequest]()
  var entertainment = ListBuffer[SetRequest]()
  
  
  def addNotificationRequest(request:SetRequest){
    notifications += request
  }
  
  
  def addEntertainmentRequest(request:SetRequest){
    entertainment += request
  }
  
}

class SetRequest{
  var id:String = null
  var dismiss_time:Long = 0
  var cards:List[String] = null
  
  def this(id:String, dismiss_time:Long, cards:List[String]){
    this()
    this.id = id
    this.dismiss_time = dismiss_time
    this.cards = cards
    
  }
  
}