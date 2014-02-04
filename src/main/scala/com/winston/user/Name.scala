package com.winston.user

import com.winston.store.MongoStore
import org.codehaus.jackson.map.ObjectMapper

class Name {
  @transient
  val mapper = new ObjectMapper()
  var string:String = null
  var scrubbed:String = null
  var mongo = new MongoStore("names")
  
  def this(string:String){
    this()
    mongo = new MongoStore("names")
    this.string = string
    this.scrubbed = scrubName(string)
  }
  
  def this(string:String, scrubbed:String){
    this()
    this.string = string
    this.scrubbed = scrubbed
  }
  
  def scrubName(name:String):String = {
    var obj = mongo.findOneSimple("name", name)
    if(obj == null)
      return name
    var cleanJson = obj.toString().replaceAll("\\r", " ").replaceAll("\\n", " ").trim
    val nameJson = mapper.readTree(cleanJson);	
    
    return nameJson.get("pronounce").asText()
  }
}