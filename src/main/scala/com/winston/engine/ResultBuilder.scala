package com.winston.engine

import com.winston.apifacades.storygraph.GraphResult
import java.util.ArrayList
import scala.collection.JavaConversions._

class ResultBuilder {
	
	def buildResult(resultType:String, graphResult:GraphResult):QueryData = {
	  var data = new QueryData()
	  
	  graphResult.dataArray.map{
	    dataArray =>{
	      val list = new ArrayList[Object]
	      dataArray.map{
	        data =>
	          list.add(data)
	      }
	      data.addSet(list)
	    }
	  }
	  data
	}
}