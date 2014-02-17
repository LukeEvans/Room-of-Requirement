package com.reactor.parsletongue.engine.scoring

import com.reactor.parsletongue.engine.ScoredText

object Scorer {
  
  def cosinSimilarity(query:String, comparison:String):Double = {
    0.0
  }
  
  def contained(query:String, phrase:ScoredText):Double ={
    query.contains(phrase.text) match {
      case true => phrase.score
      case false => 0.0
    }
  }
}