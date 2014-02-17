package com.reactor.parsletongue.engine

import scala.collection.JavaConversions._
import com.fasterxml.jackson.databind.JsonNode
import com.winston.store.MongoStore
import com.winston.utlities.Tools
import scala.collection.mutable.ListBuffer
import com.reactor.parsletongue.engine.scoring.Scorer

case class ScoredText(text:String, score:Double);
case class ActionMethod(method:String, parameters:String)

class Topic(val topic:String) {
  private val mongo = new MongoStore("Prime-Speech_Topics")
  private val json = Tools.objectToJsonNode(mongo.findOneSimple("topic", topic))
  val keywords:List[ScoredText] = getList("keywords", json)
  val phrases:List[ScoredText] = getList("phrases", json)
  val exactPhrases:List[ScoredText] = getList("phrases", json)
  val actions:List[ActionMethod] = getActionList("actions", json)
  
  
  // Score query:String against topic 
  def scoreQuery(query:String):Double = {
    var score = 0.0
    
    exactPhrases map{
      exactPhrase => score += Scorer.contained(query, exactPhrase)
    }
    
    phrases map{
      phrase => score += Scorer.contained(query, phrase)
    }
    
    keywords map{
      keyword => score += Scorer.contained(query, keyword)
    }
    
    score
  }

    // create List[ActionMethod] from name field in JsonNode
  private def getActionList(name:String, node:JsonNode):List[ActionMethod] = {
    node.has(name) match{
      case true => nodeArrayToMethodList(node.get(name))
      case false =>
        println("Error: Failed to get " + name)
        null
    }
  }
  
  // create List[ScoredText] from name field in JsonNode
  private def getList(name:String, node:JsonNode):List[ScoredText] = {
    node.has(name) match{
      case true => nodeArrayToTextList(node.get(name))
      case false =>
        println("Error: Failed to get " + name)
        null
    }
  }
  
  // JsonNode Array to List[ScoredText]
  private def nodeArrayToTextList(keywords:JsonNode):List[ScoredText] = {
    var textList:List[ScoredText] = Nil
    
    keywords map {
      node => getScoredText(node) match{
        case Some(scoredText) => scoredText :: textList
        case None => println("Failure: getScoredText()")
      }
    }  
    textList
  }
  
   // JsonNode Array to List[ActionMethod]
  private def nodeArrayToMethodList(keywords:JsonNode):List[ActionMethod] = {
    var textList:List[ActionMethod] = Nil
    
    keywords map {
      node => getActionMethod(node) match{
        case Some(actionMethod) => actionMethod :: textList
        case None => println("Failure: getScoredText()")
      }
    }  
    textList
  }
  
  // JsonNode to Option[ScoredText] 
  private def getScoredText(node:JsonNode):Option[ScoredText] ={
    if(node.has("text") && node.has("score"))
      Some(ScoredText(node.get("text").asText(), node.get("score").asDouble()))
    else
      None    
  }
  
  private def getActionMethod(node:JsonNode):Option[ActionMethod] ={
	if(node.has("method") && node.has("parameters"))
      Some(ActionMethod(node.get("method").asText(), node.get("parameters").asText()))
    else
      None 
  }
}