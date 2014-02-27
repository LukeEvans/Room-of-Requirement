package com.reactor.parsletongue.engine

import org.specs2.mutable._
import org.specs2.mock._

class EngineSpec extends Specification {
  val weather = new Topic("weather")
  val briefing = new Topic("briefing") 
  val nearby = new Topic("nearby")
  val ent = new Topic("entertainment")
  val photo = new Topic("photos")
  val storyGraph = new Topic("graph")
  val stocks = new Topic("stocks")
  val support = new Topic("support")
  val video = new Topic("video")
  
  val types = List(weather, briefing, nearby, ent, photo, storyGraph, stocks, support, video)
  
  "weather query" should{
    "map to weather action" in{
      evaluateQuery("What is the forecast for tomorrow?", types) must beEqualTo("weather")
    }
  }
  
  "briefing query" should{
    "map to briefing action" in{
      evaluateQuery("Can I get a briefing", types) must beEqualTo("briefing")
    }
  }
  
  "nearby query" should{
    "map to nearby action" in{
      evaluateQuery("What is nearby", types) must beEqualTo("nearby")
    }
  }
  
  "entertainment query" should{
    "map to entertainment action" in{
      evaluateQuery("I am bored", types) must beEqualTo("entertainment")
    }
  }
  
  
  def evaluateQuery(query:String, typeList:List[Topic]):String = {
    
    var highestRankedTopic = new Topic("")
    
    for(queryType <- typeList){
      if(queryType.scoreQuery(query) > highestRankedTopic.scoreQuery(query))
        highestRankedTopic = queryType
    }
    
    return highestRankedTopic.topic
  }
  
}