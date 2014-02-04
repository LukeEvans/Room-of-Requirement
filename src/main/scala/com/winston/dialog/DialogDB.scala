package com.winston.dialog

import java.util.ArrayList
import scala.collection.JavaConversions._
import com.novus.salat._
import com.novus.salat.global._
import com.winston.store.MongoStore
import org.codehaus.jackson.map.ObjectMapper


class DialogDB {
	@transient
	val mapper = new ObjectMapper()
	var mongo = new MongoStore("Prime-Dialogs")
	var dialogs = loadDialogs()
	
	def this(mongo:MongoStore){
	  this()
	  println("")
	}
	
	def containsSpeech(dialogString:String):Boolean = {
	  for(dialog <- dialogs){
	    if(dialog.containsSpeech(dialogString))
	      return true
	  }
	  false
	}
	
	def findDialog(dialogString:String):Dialog = {
	  for(dialog <- dialogs){
	    if(dialog.containsSpeech(dialogString))
	      return dialog
	  }
	  return null
	}
	
	private def loadDialogs():ArrayList[Dialog] = {
	  var dialogObjects = mongo.findAll
	  var list = new ArrayList[Dialog]
	  
	  for(obj <- dialogObjects){
		  var cleanJson = obj.toString().replaceAll("\\r", " ").replaceAll("\\n", " ").trim
		  val json = mapper.readTree(cleanJson);	
		  list.add(new Dialog(json))
	  }
	  list
	}
}