package com.reactor.parsletongue.engine.topic

import org.specs2.mutable._
import org.specs2.mock._

import com.reactor.parsletongue.engine.Topic

class TopicMock extends Specification{

  val topic = new Topic("weather")
  
  "topic name" should{
    "equal" in{
      topic.actions.size must beEqualTo(1)
    }
  }
  
    "topic actions" should{
    "equal" in{
      topic.actions.size must beEqualTo(1)
    }
  }

  
//  topic.actions must have size(1)
//  topic.exactPhrases must have size(3)
//  topic.phrases must have size(3)
//  topic.keywords must have size(3)
//  
//  topic.actions(0).method must beEqualTo("forecast")
//  topic.exactPhrases(0).text must beEqualTo("what is the weather")
//  topic.phrases(0).text must beEqualTo("what will")
//  topic.keywords(0).text must beEqualTo("weather")
}