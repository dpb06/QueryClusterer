package queryclusterer.algorithm;



import java.lang.Math;





public class KullbeckLiebler {


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
    // !!!!!!!!!!!!!!!!!NOT USING!!!!!!!!!!!!!!!!!!!!!!!
//	public static double measureCatGivenWord(String cat_str, String word_str, boolean checkStored){
//
//		Category cat = Category.getCategory(cat_str);
//		Word word = Word.getWord(word_str);
//		int cat_id = cat.getCategoryId();
//		int word_id = word.getId();
//		double result = measureCatGivenWord(cat_id, word_id, checkStored);
//
//		//Save result if not saved?
//		return result;
//	}




//	public static double measureCatGivenWord(Integer cat_id, Integer word_id, boolean checkStored){
//
//		
//		Double result = null;
//		
//		//result = measureCat(Category.getCategories().get(cat_id), )
//
//		//WORK OUT MEASURE
//		return result;		
//
//	}




	//Stand in for P(C|ws \/ wt)
	//public double measureCatGivenWordPair(String cat, String s_word, String t_word, boolean checkStored){

	//}




	/**
	 * Works out algorithm 9
	 */

	public double weightedAverageKL(DistributionContainer s_word, DistributionContainer t_word){

		double totalAvgKL = 0;

		// index of word in the word results table



		//They are the same word return -2

		if(s_word == t_word){
			return -2;
		}
//		System.out.println(Category.getNumberOfCategories());
		//Works out their algorithm 9
		for(Category cat : Category.getCategories()){

			// works out algorithm 7 for both of them (simplified for now, only averages)
			Distribution s_dist = s_word.getDistribution();
			Distribution t_dist = t_word.getDistribution();
			
			double pc_st = getJoinedProbability(cat, s_dist, t_dist);
			
			double klS_T = KLDivStep(cat, s_word, t_word, pc_st);
			double klT_S = KLDivStep(cat, t_word, s_word, pc_st);
			
			double avgKL = (klS_T + klT_S)/2;

			totalAvgKL += avgKL;
		}	
		if (totalAvgKL == 0)
			System.out.println("Wat "+s_word+"|"+t_word);
		return totalAvgKL;
	}




	public double getJoinedProbability(Category cat, Distribution s_dist, Distribution t_dist){
		Double joinedProb = null;
		int cat_id = cat.getCategoryId();
		
		double pc_s = s_dist.getDistributionArray()[cat_id];
		double pc_t = t_dist.getDistributionArray()[cat_id];		
		
		joinedProb = (pc_s + pc_t)/2;
		
		return joinedProb;
	}




	/**	 works out one step of algorithm 8

	 * @arg pcst  the likelihood of being s_word or t_word (either of them) 

	 *		 That is , the probability of getting c when you have s_word or t_word

	 */

	public double KLDivStep(Category cat, DistributionContainer s_word, DistributionContainer t_word, double pc_st){
		
		int cat_id = cat.getCategoryId();
		
		//work out the relative probabilities for each word
		double pc_s = s_word.getDistribution().getDistributionArray()[cat_id];
		double pc_t = t_word.getDistribution().getDistributionArray()[cat_id];	
		double divByZeroPenalty = 10;
		double log_pc_s_DIV_pc_t = protectedLogDiv(pc_s, pc_t, s_word.toString() +" over "+t_word.toString(), divByZeroPenalty);

		
		return pc_s * log_pc_s_DIV_pc_t;
	}


	//	 works out log(a/b) (part of algorithm 8)
	// 		returns a large value (currently stored in penalty) 
	//		 if the second value is zero when the first is non-zero
	public double protectedLogDiv(double a, double b, String errorString, double penaltyForDivByZero){

		if(a != 0 && b == 0){
			//System.out.println("Protected log caught: ("+a+"/"+b+") Extra info: "+((errorString==null)?"none":errorString));
			return penaltyForDivByZero;

		}else{
			return Math.log(a/b);
		}
	}
}