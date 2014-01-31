package com.winston.commands

import org.specs2.mutable._
import com.winston.engine.query.EngineQuery
import com.winston.messaging.CommandRequest
import java.util.ArrayList
import scala.collection.JavaConversions._

class CommandSpec extends Specification {
  testCommands(getBriefingCommands, "brief")
  testCommands(getWeatherCommands, "weather")
  testCommands(getSearchCommands, "search")
  
  def testCommands(getCommands:()=>ArrayList[String], typeString:String){
    val request = new CommandRequest()
    "formulateQuery" should{
      "resolve to " + typeString in {
        for(command <- getCommands()){
          "with \"" + command +"\"" in{
            request.commandString = command
            val newQuery = new EngineQuery(request)
            newQuery.query.typeString must beEqualTo(typeString)
          }
        }
        "complete" in{
          1 must beEqualTo(1)
        }
      }
    }
  }
  
  def getBriefingCommands():ArrayList[String] = {
      var list = new ArrayList[String]
      list.add("give me a briefing")
      list.add("can I get a briefing")
      list.add("give me a report")
      list.add("give me an update please")
      
      list
  }
  
  def getWeatherCommands():ArrayList[String] = {
      var list = new ArrayList[String]
      list.add("what will the weather be like tomorrow")
      list.add("what is the forecast for tomorrow")
      list.add("will it rain tomorrow")
      list.add("what does the weather look like today")
      list.add("give me todays forecast")
     
      list
  }
  
    def getSearchCommands():ArrayList[String] = {
      var list = new ArrayList[String]
      list.add("give me some news about")
      list.add("give me some news on")
      list.add("give me some news in")
      list.add("give me some news with")
      list.add("what's new with")
      list.add("what's new about")
      list.add("what's new on")
      list.add("what's new in")
      list.add("Tell me about")
      list.add("what's up with")
      list.add("what is up with")
      list.add("any news about")
      list.add("any news on")
      list.add("any news with")
      list.add("news about")
      list.add("news on")
      list.add("news with")
      list.add("what's going on with")
      list.add("what is going on with")
      list.add("what's going on in")
      list.add("what is going on in")
      list.add("find news about")
      list.add("find me news about")
      list.add("find news on")
      list.add("find me news on")
      list.add("search for news on")
      list.add("search for news about")
      list.add("search for news with")
      list.add("who is")
      list.add("where is")
      list.add("what is")
     
      list
  }
}