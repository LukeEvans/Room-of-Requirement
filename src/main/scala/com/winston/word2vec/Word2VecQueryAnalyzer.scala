package com.winston.word2vec

import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

class Word2VecQueryAnalyzer(stopWords:Map[String, String]) {
  
  val phrases = initPhrases

  def analyzeSimilarity(phrase1:ListBuffer[String], phrase2:ListBuffer[String]):Double = {
    
    val entityWords1 = ListBuffer[String]()
    val entityWords2 = ListBuffer[String]()
    
    phrase1.foreach( word => /*if(!stopWords.contains(word.toLowerCase()))*/ entityWords1 += word )
    
    phrase2.foreach( word => /*if(!stopWords.contains(word.toLowerCase()))*/ entityWords2 += word )
    
    val word2Vec = new Word2Vec
    
    val phraseVector1 = word2Vec.sumVector(entityWords1.toList)
    
    val phraseVector2 = word2Vec.sumVector(entityWords2.toList)
    
    word2Vec.cosine(phraseVector1, phraseVector2)
  }
  
  def initPhrases:Map[String, List[String]] = {
    
    val newPhrases = Map[String, List[String]]()
    
    newPhrases.put("how are my stocks doing", List("how", "are", "my", "stock", "prices", "doing"))
    
    newPhrases.put("give me a stocks update", List("give", "me", "a", "stocks", "update"))
    
    newPhrases.put("How is the nasdaq doing", List("how", "is", "the", "nasdaq", "doing"))
    
    newPhrases.put("how is the stock market doing", List("how", "is", "the", "stock", "market", "doing"))
    
    newPhrases.put("show me stock prices for today", List("show", "me", "stock", "prices", "for", "today"))
    
    
    newPhrases.put("what will the weather be like today", List("what", "will", "the", "weather", "be", "like", "today"))
    
    newPhrases.put("show me the weather forecast", List("show", "me", "the", "weather", "forecast"))
    
    newPhrases.put("will it snow tomorrow", List("will", "it", "snow", "tomorrow"))
    
    newPhrases.put("what will the temperature be like for tomorrow", List("what", "will", "the", "temperature", "be", "like", "for", "tomorrow"))
    
    newPhrases.put("what does the weather look like", List("what", "does", "the", "weather", "look", "like"))
    
    
    newPhrases.put("can you give me a briefing", List("can", "you", "give", "me", "a", "briefing"))
    
    newPhrases.put("give me a news update", List("give", "me", "a", "news", "update"))
    
    newPhrases.put("give me a report", List("give", "me", "a", "report"))
    
    
    newPhrases.put("show me some _ nearby", List("show", "me", "some", "nearby"))
    
    newPhrases.put("what are some _ around here", List("what", "are", "some", "around", "here"))
    
    newPhrases.put("where are some _ close by", List("where", "are", "some", "close", "by"))

    
    newPhrases.put("show me some pictures of _", List("show", "me", "some", "pictures", "of"))
    
    newPhrases.put("photos of _", List("photos", "of"))
    
    newPhrases.put("get me some images of _", List("get", "me", "some", "images", "of"))
    
    
    newPhrases.put("show me some videos of _", List("show", "me", "some", "pictures", "of"))
    
    newPhrases.put("recordings of _", List("photos", "of"))
    
    newPhrases.put("get me some videos of _", List("get", "me", "some", "images", "of"))      
    
    
    return newPhrases
  }
  
}