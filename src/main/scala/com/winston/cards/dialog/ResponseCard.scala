package com.winston.cards.dialog

import com.winston.cards.Card
import com.winston.user.Name
import com.winston.dialog.Dialog

class ResponseCard extends Card{
	val `type` = "prime_speech"
	var text:String = null
	var speech:String = null
	
	def this(text:String, speech:String, name:Name){
	  this()
	  this.text = text
	  this.speech = speech
	  replaceNames(name)
	}
	
	def this(dialog:Dialog, name:Name){
	  this()
	  var response = dialog.getResponse()
	  text = response.text
	  speech = response.speech
	  replaceNames(name)
	}
	
	def replaceNames(name:Name):Unit = {
		if(name == null)
		  return
		  
		text = text.replaceAll("_USER_", name.string)
		speech = speech.replaceAll("_USER_", name.scrubbed)
	}
}