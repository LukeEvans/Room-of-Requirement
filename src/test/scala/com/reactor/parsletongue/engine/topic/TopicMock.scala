package com.reactor.parsletongue.engine.topic

import org.specs2.mutable._
import org.specs2.mock._

import com.reactor.parsletongue.engine.Topic

class TopicMock extends Specification{

  val topic = new Topic("weather")
  
  "topic name" should{
    "equal" in{
      topic.topic must beEqualTo("weather")
    }
  }
  
  "topic word size" should{
	  "equal" in{
	    topic.keywords.size must beEqualTo(3)
	  }
  }
  
  "topic phrase size" should{
	  "equal" in{
	    topic.phrases.size must beEqualTo(3)
	  }
  }
  
  "topic exact phrase size" should{
	  "equal" in{
	    topic.exactPhrases.size must beEqualTo(3)
	  }
  }
  
    "topic actions" should{
    "equal" in{
      topic.actions.size must beEqualTo(1)
    }
  }
    
}