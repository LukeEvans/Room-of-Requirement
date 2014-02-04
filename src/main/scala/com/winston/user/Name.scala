package com.winston.user

import com.winston.store.MongoStore

class Name {
  var string:String = null
  var scrubbed:String = null
  var mongo = new MongoStore("names")
  
  def this(string:String){
    this()
    mongo = new MongoStore("names")
    this.string = string
    
    
  }
}