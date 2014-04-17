package com.winston.word2vec

import com.winston.nlp.NLPWordSet
import com.winston.elasticsearch.ElasticSearch
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import com.winston.messaging.WordSetContainer
import scalaz.std.set
import com.winston.messaging.StringContainer
import scala.util.Failure
import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global


class WordVectorAnalyzerActor(nlpActor:ActorRef) extends Actor with ActorLogging {

  val stopWords = ElasticSearch.getStopPhrasesMap
  
  def receive = {
    case string:String => 
      	process(string, sender)

      
    case _:Any => 
      println("Wrong message received")

  }
  
  def process(string:String, origin:ActorRef){
    implicit val timeout = Timeout(10 seconds)
    
    val query = (nlpActor ? StringContainer(string)).mapTo[WordSetContainer]
      
    query.onComplete{
      case Success(s) =>{
        
        val analyzer = new Word2VecQueryAnalyzer(stopWords)
      
        val phrases = analyzer.phrases
      
        println()
        println()
        
        phrases.foreach{
          phrase =>
        	println("Phrase: " + phrase._1)
          
        	
        	val newSet = s.wordSet.set.map(word => word.string)
          
        	val score = analyzer.analyzeSimilarity(newSet.to[ListBuffer], phrase._2.to[ListBuffer])
          
        	println("Similarity Score: " + score)
        	println()
        	println()
        }
        
      }
        
      case Failure(e) => 
    }
      

    
  }
  
}