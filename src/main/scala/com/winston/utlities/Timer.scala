package com.winston.utlities

class Timer {
	val start = System.currentTimeMillis()
	
	def stopAndPrint(name:String) = {
	  println(name+ ": " + (System.currentTimeMillis() - start))
	}
}