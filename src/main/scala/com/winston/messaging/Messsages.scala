package com.winston.messaging

import com.winston.engine.Result
import com.winston.engine.QueryData
import com.winston.nlp.NLPWordSet

class Message extends Serializable

trait request
trait response

case class RequestContainer(commandRequest:CommandRequest) extends request
case class ResponseContainer(response:String) extends response
case class DataContainer(data:QueryData) extends response

case class StringContainer(string:String) extends Message
case class WordSetContainer(wordSet:NLPWordSet) extends Message