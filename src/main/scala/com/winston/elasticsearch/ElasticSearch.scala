package com.winston.elasticsearch

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import com.winston.utlities.Tools

object ElasticSearch {

  val stopPhrasesURL = "http://ec2-54-242-250-185.compute-1.amazonaws.com:9200/stop/_search?size=500"
    
  
  def getStopPhrases():ListBuffer[String] = {
    
    val response = Tools.fetchURL(stopPhrasesURL)
    
    val it = response.path("hits").path("hits").iterator()
    
    val phrases = ListBuffer[String]()
    
    while (it.hasNext()) {
      try {
    	val hit = it.next();
		  
    	val phrase = hit.path("_source").path("title").asText().toLowerCase();
    	
    	if (!phrases.contains(phrase))
    	  phrases += phrase
    	  
    	  
      } 
    } 
    
    return phrases
  }
  
  def getStopPhrasesMap():Map[String, String] = {
    
	val response = Tools.fetchURL(stopPhrasesURL)
    
    val it = response.path("hits").path("hits").iterator()
    
    val phraseMap = Map[String, String]()
    
    while (it.hasNext()) {
      try {
    	val hit = it.next();
		  
    	val phrase = hit.path("_source").path("title").asText().toLowerCase();
    	
    	if (!phraseMap.contains(phrase))
    	  phraseMap += ((phrase, phrase))
    	  
      } 
    } 
    
    return phraseMap
  }
}