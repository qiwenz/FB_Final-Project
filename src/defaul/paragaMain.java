package defaul;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class paragaMain {

 /* public static void main(String[] args) {

	ArrayList<Sentence> Answers=new ArrayList<Sentence>();
	
	
    HashMap<String, Integer> query = new HashMap<String, Integer>();
    query.put("classical", 1);
    query.put("music", 1);
    query.put("is", 1);
    query.put("dying", 1);

 //   List<HashMap<String, Integer>> answerDictionary = new ArrayList<HashMap<String, Integer>>();
    
    
    HashMap<String, Integer> answer = new HashMap<String, Integer>();
    answer.put("everybody", 1);
    answer.put("knows", 1);
    answer.put("classical", 1);
    answer.put("music", 1);
    answer.put("when", 1);
    answer.put("they", 1);
    answer.put("hear", 1);
    answer.put("it", 1);
    // answer.put("dying", 1);
    // answer.put("is", 1);

    Sentence sent= new Sentence("11","11",answer,0);
    Answers.add(sent);
    
    
    answer = new HashMap<String, Integer>();
    answer.put("everybody", 1);
    answer.put("knows", 1);
    answer.put("classical", 1);
    answer.put("music", 1);
    answer.put("when", 1);

    sent= new Sentence("22","22",answer,0);
    Answers.add(sent);
    
    answer = new HashMap<String, Integer>();
    answer.put("everybody", 1);
    answer.put("knows", 1);
    answer.put("classical", 1);
    answer.put("music", 1);
    answer.put("when", 1);
    answer.put("they", 1);
    answer.put("hear", 1);
    answer.put("it", 1);
    answer.put("dying", 1);
    answer.put("is", 1);

    sent= new Sentence("33","3",answer,0);
    Answers.add(sent);
    
    
    
  Answers =  CandidateAnswerSelect(query,Answers);
    for (int i=0;i<Answers.size();i++){
		  System.out.println(Answers.get(i).getSentScore());
	  }
    
  }
    
  /*  learnScoreWeights myInstance = new learnScoreWeights();
    myInstance.train(query, answerDictionary);
    System.out.println("\n------------------------------------");
    System.out.print("\nFinal Weight Vector: ");
    myInstance.printWeights();
    */
    
    //unsupervisedLearningScores unL = new unsupervisedLearningScores();
    //unL.returnClusterScores(query, answerDictionary);
    
  
  
 public ArrayList<Sentence>  CandidateAnswerSelect(HashMap<String, Integer> query, ArrayList<Sentence> Sent)
 {
	 List<HashMap<String, Integer>> answerDictionary = new ArrayList<HashMap<String, Integer>>();
	 
	 for(int i=0;i<Sent.size();i++){
     Map<String, Integer> answer =new HashMap<String,Integer>();
     answer=Sent.get(i).getMp();
     answerDictionary.add((HashMap<String, Integer>) answer);
	 }	 	 
	 
	 learnScoreWeights myInstance = new learnScoreWeights();
	    myInstance.train(query, answerDictionary);
	    //System.out.println("\n------------------------------------");
	    //System.out.print("\nFinal Weight Vector: ");
	    myInstance.printWeights();
	  double weights[] = myInstance.getWeights();
	  for (int i=0;i<Sent.size();i++){
		  //System.out.println("hohohohohohohohohohohohoho--------" + i);
		  Sent.get(i).setSentScore(weights[i]);
		  //System.out.println(Sent.get(i).getSentScore());
	  }
	  	  
	  Collections.sort(Sent, new Comparator<Sentence>(){
	      public int compare(Sentence a, Sentence b){
	        boolean res = (b.getSentScore() - a.getSentScore()) > 0;
	        return res? 1:-1;
	      }
	  });		  
	  
	  
	 
		 		  
	 
	  
	  
	 return Sent;}
  
}
