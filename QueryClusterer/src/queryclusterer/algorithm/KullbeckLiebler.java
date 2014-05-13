package queryclusterer.algorithm;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class KullbeckLiebler {




	//Maps a Word to the id that will be used as the id in
	// all storage of results 
	Map<String, Integer> wordToIDMap = new HashMap<String, Integer>();


	//Maps a Catagory to the id that will be used as the id in 
	// all storage of results
	public static Map<String, Integer> catToIDMap = new HashMap<String, Integer>();


	//for storing a words relation to a catagory
	//mc_w.get(i).get(j) will get catagory i, word_id j 
	//storage of measureCatGivenWord results 
	List<Map<Integer, Double>> measureCat_Word = new ArrayList<Map<Integer, Double>>(); 

	//For storing measureCatGivenWordPair
	List<Map<Integer, Map<Integer, Double>>> measureCat_WordPair = new ArrayList<Map<Integer, Map<Integer, Double>>>();










	// will return a low value for extremely similar distributions
	// and high values for extremely dissimilar distributions 
	public double compareDistributions( ){

		Double comparedDists = null;

		return comparedDists;
	}





	/*
	 *	 1-	 Check for an already calculated value (if not working out all from scratch) 
	 *		2-	 Return ~p(C given w)
	 *		 (currently do any kind of measure)
	 */

	/**
			 REMEMBER TO SAVE ANYTHING YOU WORK OUT. 
	 */

	public double measureCatGivenWord(String cat, String word, boolean checkStored){

		Integer cat_id = catToIDMap.get(cat);
		Integer word_id = wordToIDMap.get(word);
		double result = measureCatGivenWord(cat_id, word_id, checkStored);

		//Save result if not saved?
		return result;




	}




	public double measureCatGivenWord(Integer cat_id, Integer word_id, boolean checkStored){

		Double result = null;

		if (checkStored){

			Double storedResult = measureCat_Word.get(cat_id).get(word_id);

			if (storedResult != null){   //or not filled in (depends on what the empty value will be)
				result = storedResult;
				return result;
			}
		}

		//WORK OUT MEASURE
		measureCat_Word.get(cat_id).put(word_id, result);		

	}




	//Stand in for P(C|ws \/ wt)
	public double measureCatGivenWordPair(String cat, String s_word, String t_word, boolean checkStored){

	}




	/**
	 * Works out algorithm 9
	 */

	public double weightedAverageKL(String s_word, String t_word){

		double totalAvgKL = 0;

		// index of word in the word results table

		int s_index = getWordIndex(s_word);
		int t_index = getWordIndex(t_word);

		// index of the pair 
		///THESE SHOULD BOTH JUST BE THE SAME, T->S AND S->T SHOULD BE IDENTICAL
		int st_index = getPairIndex(s_word, t_word);
		int ts_index = getPairIndex(t_word, s_word);
		
		//They are the same word return a perfect match

		if(s_index == t_index){
			return 0;
		}

		//Works out their algorithm 9
		for(String cat : catagoryList){

			// works out algorithm 7 for both of them (simplified for now, only averages)
			double pc_st = getJoinedProbability(cat, s_word, t_word);
			
			double klS_T = KLDivStep(cat, s_word, t_word, pc_st);
			double klT_S = KLDivStep(cat, t_word, s_word, pc_st);
			
			double avgKL = (klS_T + klT_S)/2;

			totalAvgKL += avgKL;
		}	
		return totalAvgKL;
	}


	/**
	 * 
	 * @param s_word
	 * @return
	 */
	private int getWordIndex(String s_word) {
		int id = -1;
		if(wordToIDMap.containsKey(s_word))
			id = wordToIDMap.get(s_word);		 

		return id;
	}





	public double getJoinedProbability(String cat, String s_word, String t_word, boolean checkStored){
		Double joinedProb = null;
		if(checkStored){
			//check joined probability
			///////HERE//////
			if (joinedProb != null)
				return joinedProb;
		}
		
		
		double pc_s = measureCatGivenWord(cat, s_word, true);
		double pc_t = measureCatGivenWord(cat, t_word, true);
		
		joinedProb = (pc_s + pc_t)/2;
		
		return joinedProb;
	}




	/**	 works out one step of algorithm 8

	 * @arg pcst  the likelihood of being s_word or t_word (either of them) 

	 *		 That is , the probability of getting c when you have s_word or t_word

	 */

	public double KLDivStep(String cat, String s_word, String t_word, double pc_st){
		
		//work out the relative probabilities for each word
		double pc_s = measureCatGivenWord(cat, s_word, false);
		double pc_t = measureCatGivenWord(cat, t_word, false);
		double divByZeroPenalty = 3500;
		double log_pc_s_DIV_pc_t = protectedLogDiv(pc_s, pc_t, s_word +" over "+t_word, divByZeroPenalty);

		
		return pc_s * log_pc_s_DIV_pc_t;
	}


	//	 works out log(a/b) (part of algorithm 8)
	// 		returns a large value (currently stored in penalty) 
	//		 if the second value is zero when the first is non-zero
	public double protectedLogDiv(double a, double b, String errorString, double penaltyForDivByZero){

		if(a != 0 && b == 0){
			System.out.println("Protected log caught: ("+a+"/"+b+") Extra info: "+((errorString==null)?"none":errorString));
			return penaltyForDivByZero;

		}else{
			return Math.log(a/b);
		}
	}
}