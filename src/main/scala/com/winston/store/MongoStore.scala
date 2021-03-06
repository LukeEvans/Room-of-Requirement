package com.winston.store

import com.mongodb.casbah.MongoURI
import com.mongodb.casbah.Imports._
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.util.JSON
import java.util.ArrayList
import com.fasterxml.jackson.databind.JsonNode
import com.mongodb.DBCursor

class MongoStore(collectString:String) {
	val uri = MongoURI("mongodb://levans002:dakota1@ds031887.mongolab.com:31887/winston-db")
	val db = uri.connectDB

	val collection = db.right.get.getCollection(collectString)
	
	def findOne(queryObject:BasicDBObject):Object = {
		collection.findOne(queryObject)
	}
	
	def findOneSimple(field:String, value:String):Object = {
	  var queryObject = new BasicDBObject(field, value)
	  findOne(queryObject)
	}
	
	def findFirst():DBCursor = {
	  collection.find()
	}
	
	def findAll():ArrayList[MongoDBObject] = {
	  var list = new ArrayList[MongoDBObject]
	  var cursor = collection.find()
	  while(cursor.hasNext()){
	    list.add(cursor.next())
	  }
	  list
	}	
		
	def insert(dbObj:DBObject){
	  
	  collection.insert(dbObj)
	}
	
	
	/** Get the size of the collection */
	def size() = {
	  collection.count()
	}
	
	
	/** */
	def findAny():MongoDBObject = {
	  
	  val cursor = collection.find()
	  
	  if(cursor.hasNext())
	    return cursor.next()
	  else
	    null
	}
}