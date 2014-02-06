package com.winston.nlp

import scala.collection.JavaConversions._
import java.io.FileInputStream
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import opennlp.tools.tokenize.TokenizerModel
import opennlp.tools.tokenize.TokenizerME
import edu.stanford.nlp.pipeline.Annotation
import java.io.InputStream
import opennlp.tools.tokenize.Tokenizer
import java.util.Properties
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import com.winston.messaging.request
import java.util.ArrayList
import com.winston.messaging.request
import com.winston.messaging.request
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation
import com.winston.messaging.WordSetContainer

class NLPSplitter{
        var currentPath = System.getProperty("user.dir")
        var splitProps:Properties = new Properties();
        splitProps.put("annotators", "tokenize, ssplit");
        var splitProcessor:StanfordCoreNLP = null;
        var tokenizer:Tokenizer = null;
        
        var tokenModelIn: InputStream = null;
        
       	try { 
       		tokenModelIn = new FileInputStream("/usr/local/requirement-dist" + "/config/en-token.bin");
       	} catch {
       		case e: Exception => tokenModelIn = new FileInputStream(currentPath + "/src/main/resources/en-token.bin");
       	}
	
        var tokenModel:TokenizerModel = null
        
        def init() {
                splitProcessor = new StanfordCoreNLP(splitProps)
                println("--Splitter Created");
                tokenModel = new TokenizerModel(tokenModelIn);
                tokenizer = new TokenizerME(tokenModel);
                tokenModelIn.close()
        }
        
        def splitProcess(queryString: String):NLPWordSet = {
          if(queryString == null || queryString.equalsIgnoreCase(""))
        	  return null
        	  
           var document = new Annotation(queryString)
        		  
          splitProcessor.annotate(document)                        
          var sentenceList = document.get(classOf[SentencesAnnotation])
          
          var set = new NLPWordSet
          set.queryString = queryString
                                                
          for(sentence <- sentenceList){
        	  var sentenceString = sentence.get(classOf[TextAnnotation])
        	  set.addSentence(sentenceString)                                          
        	  val spans = tokenizer.tokenizePos(sentenceString)
        	  val tokens = tokenizer.tokenize(sentenceString)
                                                        
        	  for(idx <- 0 to spans.length-1){
        		  val span = spans(idx);
        		  set.addWord(new NLPWord(tokens(idx), "", span.getStart()))
        	  }
          } 
          set
       }
}