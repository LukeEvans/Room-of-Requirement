package com.reactor.parsletongue.engine.dialog

import com.novus.salat.annotations.raw.Key
import scala.collection.JavaConversions._
import java.util.ArrayList
import com.mongodb.BasicDBObject
import com.mongodb.BasicDBList
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat._
import com.novus.salat.global._

case class DialogObject(@Key("name") name:String, speech:BasicDBList, response:BasicDBList){
  var speechList = buildSpeechList(speech)
  var responseList = buildResponseList(response)
  
  def containsSpeech(speechString:String):Boolean = {
    for(speechObj <- speechList){
      if(speechString.equalsIgnoreCase(speechObj))
        return true
    }
    false
  }
  
  def getResponse():Speech = {
    if(response == null)
      null
    
    responseList.get(0)
  }
  
  private def buildSpeechList(dbList:BasicDBList):ArrayList[String] = {
    var list = new ArrayList[String]
    
    for(obj <- dbList){
      var text = obj.asInstanceOf[String]
      list.add(text)
    }
    list
  }
  
    private def buildResponseList(dbList:BasicDBList):ArrayList[Speech] = {
    var list = new ArrayList[Speech]
    
    for(obj <- dbList){
      var dbObj = obj.asInstanceOf[BasicDBObject]
      var text = new Speech(dbObj.get("text").asInstanceOf[String], dbObj.get("speech").asInstanceOf[String])
      list.add(text)
    }
    list
  }
}