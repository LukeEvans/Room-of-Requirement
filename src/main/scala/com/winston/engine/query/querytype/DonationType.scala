package com.winston.engine.query.querytype

import com.winston.apifacades.dumbledore.DumbledoreAPI
import java.util.ArrayList
import com.winston.engine.query.Word
import com.winston.engine.query.UserCredentials
import com.winston.engine.QueryData

class DonationType extends QueryType{
	var dumbledore = new DumbledoreAPI
	
	typeString = "donation"
	  
	override def init(){
	  wordBank = new ArrayList[Word]
	  wordBank.add(new Word("Donation", "donation", 20));
	  wordBank.add(new Word("Donations", "donations", 20));
	}
  
	override def process(query:String, creds:UserCredentials):QueryData = {
	  
	  val donationSet = dumbledore.donation
	  
	  var data = new QueryData 
	  data.addSet(donationSet)
	}
}