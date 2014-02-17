package com.winston.query

import org.specs2.mutable._
import com.winston.apifacades.winstonapi.WinstonAPI
import com.winston.engine.query.querytype.NearbyType
import com.winston.engine.query.UserCredentials
import java.util.ArrayList

class QueryTypeSpec extends Specification {
	
//  "run functions" should{
//    "run list of functions" in{
//      "with nearby querytype" in{
//        val winstonAPI = new WinstonAPI
//        val query = new NearbyType
//        
//        val funcs = List(funcOf(winstonAPI.comicCall), funcOf(winstonAPI.stocksCall(new UserCredentials)))
//        
//        val results = funcs map{fn => fn.apply}
//        
//        for(result <- results){
//          println(result.toString())
//        }
//        
//        1 must beEqualTo(1)
//      }
//    } 
//  }
  
  def funcOf(param: => ArrayList[Object]) = () => param
}