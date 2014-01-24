package com.winston.messaging

import com.winston.engine.Result
import com.winston.engine.QueryData

class Message extends Serializable

trait request
trait response

case class RequestContainer(commandRequest:CommandRequest) extends request
case class ResponseContainer(response:String) extends response
case class DataContainer(data:QueryData) extends response