package com.winston.engine.query

import java.util.ArrayList
import java.util.HashMap
import scala.collection.JavaConversions.asScalaBuffer
import com.winston.engine.query.querytype.BriefingType
import com.winston.engine.query.querytype.DialogType
import com.winston.engine.query.querytype.PhotoType
import com.winston.engine.query.querytype.QueryType
import com.winston.engine.query.querytype.SearchType
import com.winston.engine.query.querytype.VideoType
import com.winston.engine.query.querytype.WeatherType
import com.winston.nlp.NLPWordSet
import com.winston.engine.query.querytype.NearbyType
import com.winston.engine.query.querytype.SupportType
import com.winston.engine.query.querytype.EntertainmentType
import com.winston.messaging.QueryTypeContainer
import com.winston.utlities.Timer
import com.winston.engine.query.querytype.StocksType

class Categorizer {
	var types:ArrayList[QueryType] = new ArrayList[QueryType]
	types add new BriefingType
	types add new WeatherType
	types add new SearchType
	types add new PhotoType
	types add new VideoType
	types add new NearbyType
	types add new SupportType
	types add new EntertainmentType
	types add new StocksType
	
	def this(string:String) = {
	  this()
	  types = new ArrayList[QueryType]
	  var brief = new BriefingType
	  brief.init
	  types.add(brief)
	  var weather = new WeatherType
	  weather.init
	  types.add(weather)
	  var search = new SearchType
	  search.init
	  types.add(search)
	  var photo = new PhotoType
	  photo.init
	  types.add(photo)
	  var video = new VideoType
	  video.init
	  types.add(video)
	  var dialog = new DialogType
	  dialog.init
	  types.add(dialog)
	  var nearby = new NearbyType
	  nearby.init
	  types.add(nearby)
	  var support = new SupportType
	  support.init
	  types.add(support)
	  var ent = new EntertainmentType
	  ent.init
	  types.add(ent)
	  var stocks = new StocksType
	  stocks.init
	  types.add(stocks)
	}
	
	//Figure out QueryType based off wordset
	def formulate(set:NLPWordSet):QueryTypeContainer = {  
	  var typeScores = new HashMap[String, Double]

	  for(queryType <- types)
	    typeScores.put(queryType.typeString, queryType.rank(set))
	    
	  QueryTypeContainer(getTypeFromList(getTopRankedTypeString(typeScores)))
	}
	
	def getTopRankedTypeString(map:HashMap[String, Double]):String = {
	  
	 var it = map.entrySet().iterator
	 
	 //Tuple Format:  (TYPE, SCORE)
	 var topValue = ("search", 7.0)
	 while(it.hasNext){
	   var pairs = it.next()
	   
	   if(topValue._2 < pairs.getValue())
	     topValue = (pairs.getKey(), pairs.getValue())   
	 }
	 
	 topValue._1
	}
	
	def getTypeFromList(queryTypeString:String):QueryType ={
	  for(qType <- types){
	    if(qType.typeString.equalsIgnoreCase(queryTypeString))
	      return qType
	  }
	  return null
	}
}