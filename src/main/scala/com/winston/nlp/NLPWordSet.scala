package com.winston.nlp

import scala.collection.JavaConversions._
import java.util.ArrayList

class NLPWordSet {
	var set:ArrayList[NLPWord] = null
	var sentences:ArrayList[String] = null
	
	def this(set:ArrayList[NLPWord]){
	  this()
	  this.set = set
	}
	
	def addSentence(sentence:String){
	  if(sentences == null)
	    sentences = new ArrayList[String]
	  
	  sentences.add(sentence)
	}
	
	def addWord(word:NLPWord){
	  if(set == null)
	    set = new ArrayList[NLPWord]
	  
	  set.add(word)
	}
	
	def setContains(wordString:String):Boolean = {
	  for(word <- set){
	    if(word.equals(wordString))
	      return true
	  }
	  false
	}
	
	def print(){
	  println("Sentences:")
	  for(sent <- sentences)
	    println(sent)
	  
	  println("Words: ")
	  for(word <- set){
	    println("String: " + word.string)
	    println("Lemma: " + word.lemma)
	    println("Index: " + word.index)
	  }
	}
}