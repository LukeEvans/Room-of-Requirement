package com.winston.dialog

import java.util.ArrayList
import scala.collection.JavaConversions._
import com.novus.salat._
import com.novus.salat.global._
import com.winston.store.MongoStore


class DialogDB {
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
	
	def findDialog(dialogString:String):DialogObject = {
	  for(dialog <- dialogs){
	    if(dialog.containsSpeech(dialogString))
	      return dialog
	  }
	  return null
	}
	
	private def loadDialogs():ArrayList[DialogObject] = {
	  var dialogObjects = mongo.findAll
	  var list = new ArrayList[DialogObject]
	  
	  for(obj <- dialogObjects)
	    list.add(grater[DialogObject].asObject(obj))
	  
	  list
	}
}