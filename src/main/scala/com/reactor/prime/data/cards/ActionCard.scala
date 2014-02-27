package com.reactor.prime.data.cards

import com.winston.utlities.Tools

class ActionCard {
	var id:String = Tools.generateHash("support")
	val `type` = "action"
	var text:String = null
	var subtext:String = null
	var speech:String = null
	var action:String = "support_email"
}