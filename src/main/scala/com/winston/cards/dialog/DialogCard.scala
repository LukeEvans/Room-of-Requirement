package com.winston.cards.dialog

import com.winston.cards.Card
import com.winston.dialog.DialogObject

class DialogCard extends Card{
	val `type` = "prime_speech"
	var text:String = null
	var speech:String = null
	
	def this(text:String, speech:String) = {
	  this()
	  this.text = text
	  this.speech = text
	}
	
	def this(dialog:DialogObject){
	  this()
	  var response = dialog.getResponse()
	  text = response.text
	  speech = response.speech
	}
}