package com.winston.cards.dialog

import com.winston.cards.Card
import com.winston.user.Name
import com.winston.dialog.Dialog
import com.winston.utlities.Tools

class ResponseCard extends Card{
  	var id:String = null
	val `type` = "prime_speech"
	val story_type = "prime_speech"
	var text:String = null
	var speech:String = null
	
	def this(text:String, speech:String, name:Name){
	  this()
	  this.text = text
	  this.speech = speech
	  replaceNames(name)
	  id = Tools.generateHash(text)
	}
	
	def this(dialog:Dialog, name:Name){
	  this()
	  var response = dialog.getResponse()
	  text = response.text
	  speech = response.speech
	  replaceNames(name)
	  id = Tools.generateHash(text)
	}
	
	def replaceNames(name:Name):Unit = {
		if(name != null){
		  text = text.replaceAll("_USER_", name.string)
		  speech = speech.replaceAll("_USER_", name.scrubbed)
		}
		else{
	      text = text.replaceAll(" _USER_", "")
		  speech = speech.replaceAll(" _USER_", "")
		}
	}
}