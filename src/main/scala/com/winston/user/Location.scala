package com.winston.user

class Location {
	var loc:String = null
	var lat:Double = 0
	var long:Double = 0
	
	def this(loc:String){
	  this()
	  this.loc = loc
	  val (lat,long) = getLatAndLong(this.loc)
	  this.lat = lat
	  this.long = long
	}
	
	def this(loc:String, lat:Double, long:Double){
	  this()
	  this.loc = loc
	  this.lat = lat
	  this.long = long
	}
	
	private def getLatAndLong(locString:String):(Double, Double) = {
	  var split = locString.split(",")
	  if(split.size != 2)
	    null

	  (split(0).toDouble, split(1).toDouble)
	}
}