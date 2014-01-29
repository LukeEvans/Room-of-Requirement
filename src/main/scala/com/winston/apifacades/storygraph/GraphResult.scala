package com.winston.apifacades.storygraph

import com.fasterxml.jackson.databind.JsonNode
import java.util.ArrayList

/** StoryGraph Response Object
 * 
 */
class GraphResult {
  var data:JsonNode = null
  var free_text:String = null
  
  
  def this(node:JsonNode){
    this()
    if(node.has("data"))
      data = node.get("data")
  }
  
  def getTopCandidate():Object = {
    if(data.has("keywords")){
      if(data.get("keywords").has(0)){
        if(data.get("keywords").get(0).get("candidates").has(0)){
          return data.get("keywords").get(0).get("candidates").get(0)
        }
      }
    }    
    return null
  }
  
  def getNews():ArrayList[Object] = {
    if(data.has("confluence")){
      if(data.get("confluence").has("topic_news")){
        var newsList = new ArrayList[Object]
        
        for (i <- 0 to (data.get("confluence").get("topic_news").size() - 1))
          newsList.add(data.get("confluence").get("topic_news").get(i))
        
        return newsList
      }
    }
    return null
  }
  
    def getTweets():ArrayList[Object] = {
    if(data.has("confluence")){
      if(data.get("confluence").has("topic_twitter")){
        var newsList = new ArrayList[Object]
        
        for (i <- 0 to (data.get("confluence").get("topic_twitter").size() -1))
          newsList.add(data.get("confluence").get("topic_twitter").get(i))
        
        return newsList
      }
    }
    return null
  }

}