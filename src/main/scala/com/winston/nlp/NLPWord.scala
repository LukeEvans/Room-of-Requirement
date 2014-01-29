package com.winston.nlp

class NLPWord {
	var string:String = ""
	var lemma:String = ""
	var index:Int = 0
	
	def this(wordString:String, wordLemma:String, wordIndex:Int){
	  this()
	  string = wordString
	  lemma = wordLemma
	  index = wordIndex
	}
	
	def equals(wordString:String):Boolean = {
	  if(string.equals(wordString) || 
	      lemma.equalsIgnoreCase(wordString)) true else false
	}
}