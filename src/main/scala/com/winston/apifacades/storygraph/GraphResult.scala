package com.winston.apifacades.storygraph

import com.fasterxml.jackson.databind.JsonNode
import java.util.ArrayList
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

/** StoryGraph Response Object
 * 
 */
class GraphResult {
  var data:JsonNode = null
  var free_text:String = null
  var dataArray = new ArrayList[ArrayList[JsonNode]]()
  
  
  def this(node:JsonNode){
    this()
//    if(node.has("data")){
//      data = node.get("data")
//      dataArray = buildData(data)
//    }
    if(!node.isEmpty && node != null){
      dataArray = buildData(node)
    }
  }
  
  def buildData(dataNode:JsonNode):ArrayList[ArrayList[JsonNode]] = {
    if(dataNode == null) return null
    
    val dataList = new ArrayList[ArrayList[JsonNode]]()
    
    for(nodeData <- dataNode){
      val list = new ArrayList[JsonNode]()
      
      for(data <- nodeData)
    	  list.add(data)
      if(!list.isEmpty)
        dataList.add(list)
    }
    dataList
  }
  
  def getYoutube():ArrayList[Object] = {
    if(dataArray == null) return null
    
    val list = new ArrayList[Object]
    
    for(data <- dataArray){
      for(d <- data){
        if(d.has("type")){
          if(d.get("type").asText().equalsIgnoreCase("youtube"))
            list.add(d)
        }
      }
    }
    if(list.isEmpty())
      null
    list
  }
  
//  def getTopCandidate():Object = {
//    if(data.has("keywords")){
//      if(data.get("keywords").has(0)){
//        if(data.get("keywords").get(0).get("candidates").has(0)){
//          return data.get("keywords").get(0).get("candidates").get(0)
//        }
//      }
//    }    
//    return null
//  }
//  
//  def getNews():ArrayList[Object] = {
//    if(data.has("confluence")){
//      if(data.get("confluence").has("topic_news")){
//        var newsList = new ArrayList[Object]
//        
//        for (i <- 0 to (data.get("confluence").get("topic_news").size() - 1))
//          newsList.add(data.get("confluence").get("topic_news").get(i))
//        
//        return newsList
//      }
//    }
//    return null
//  }
//  
//  def getTweets():ArrayList[Object] = {
//    if(data.has("confluence")){
//      if(data.get("confluence").has("topic_twitter")){
//        var newsList = new ArrayList[Object]
//        
//        for (i <- 0 to (data.get("confluence").get("topic_twitter").size() -1))
//          newsList.add(data.get("confluence").get("topic_twitter").get(i))
//        
//        return newsList
//      }
//    }
//    return null
//  }
//  
//  def getYoutube():ArrayList[Object] = {
//    if(data.has("confluence")){
//      if(data.get("confluence").has("topic_youtube")){
//        var newsList = new ArrayList[Object]
//        
//        for (i <- 0 to (data.get("confluence").get("topic_youtube").size() -1))
//          newsList.add(data.get("confluence").get("topic_youtube").get(i))
//        
//        return newsList
//      }
//    }
//    return null
//  }

}