package com.winston.cards.dialog

import com.winston.cards.Card

class DialogCard extends Card{
	var `type`:String = null
	var text:String = null
	var speech:String = null
	
	def this(text:String, speech:String, `type`:String) = {
	  this()
	  this.`type` = `type`
	  this.text = text
	  this.speech = text
	}
}