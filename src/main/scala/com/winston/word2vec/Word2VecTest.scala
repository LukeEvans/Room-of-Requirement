package com.winston.word2vec

//object Word2VecTest {
//
//  def main(args:Array[String]){
//    
//    println("running w2v test")
//    
//    val w2V = new Word2Vec
//    
//    val v =  w2V.vector("weather")
//    
//    println("loaded vector")
//      
//    val nearestNeighbs = w2V.nearestNeighbors(v)
//    
//    println("Nearest Neighbors")
//    
//    nearestNeighbs.foreach{ neighb => println("Term: " + neighb._1 + "  " + "Distance: " + neighb._2) }
//    
//    println("nearestNeighbs ran")
//  }
//}