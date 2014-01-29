package com.winston.engine.query

class Word {
  var string:String = ""
  var lemma:String = ""
  var weight:Double = 0
  
  def this(wordString:String, lemmaString:String, wordWeight:Double){
    this()
    string = wordString
    lemma = lemmaString
    weight = wordWeight
  }
}