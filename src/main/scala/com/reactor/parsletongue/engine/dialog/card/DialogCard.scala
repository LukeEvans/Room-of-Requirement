package com.reactor.parsletongue.engine.cards.dialog

import com.winston.cards.Card
import com.winston.dialog.DialogObject
import com.winston.utlities.Tools

class DialogCard extends Card{
	val `type` = "prime_speech"
	var id:String = null
	var text:String = null
	var speech:String = null
	
	def this(text:String, speech:String) = {
	  this()
	  this.text = text
	  this.speech = text
	  this.id = Tools.generateHash(text)
	}
	
	def this(dialog:DialogObject){
	  this()
	  var response = dialog.getResponse()
	  text = response.text
	  speech = response.speech
	  this.id = Tools.generateHash(text)
	}
}