package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/** 
 * @author Defenestrayte
 * 
 * Construction of Word
 * 		Read First token of line, -> query -> 
 * 			-> for each query expand
 *
 */
public class Word implements DistributionContainer {
	private boolean TEST = true;
	
	
	public static List<Word> WORDS = new ArrayList<Word>();
	private int id = -1;
	private Distribution dist = null;

	private int count = 1; // only for TEST
	private static double totalCount = 1;
	
	private String word;

	public int getId() {
		
		return id;
	}
	
	public Word(String word){
		this.word = word;
		this.id = WORDS.size(); // first is zero, 
		WORDS.add(this);
		Word.totalCount++;

	}
	
	private class SortById implements Comparator<Word>{


		public int compare(Word o1, Word o2) {
			// TODO Auto-generated method stub
			return o2.getId() - o1.getId();
		}
	}


	public String getWord(){
		return word;
	}
	// called by query.mergedDist // for test
	public Distribution getDistribution(Query query, List<Category> categories){
		
		//dist = new Distribution(this, categories, query);
		dist = testDistribution(query, categories);
		return dist;
	}
	
	//Test algorithm // called by this.getDist
	public Distribution testDistribution(Query query, List<Category> categories){
		if(dist != null){
			return dist;
		}
		
		
		double[] probArray = new double[categories.size()];
		
		for(Category category : categories){
			double bias = 1.0;
			if(query.getVotedCategories().contains(category)){
				bias = 3.4;
			}
			double measure = (0.005+ (0.1* Math.random())) * bias*(count/totalCount); // cant be zero
			
			
			//System.out.println("CategoryPos"+category.getCategoryId()+"/"+Category.getNumberOfCategories());
			//System.out.println(Arrays.toString(probArray));	
			
			probArray[category.getCategoryId()] = measure;
			
			//System.out.println(Arrays.toString(probArray));		
			//System.out.println();
		}
		
		dist = new Distribution(probArray);
		return dist;
	}
	
	
	
	public int getCount(){
		return count;
	}

	public static Word getWord(String _word){
		
		for(Word existWord : WORDS){
			if(existWord.getWord().equals(_word) ){
				
				existWord.count++;
				Word.totalCount++;
				System.out.println(existWord.count+"/"+Word.totalCount);
				return existWord;
			}
		}		
		return new Word(_word);
	}

	@Override
	public Distribution getDistribution() {
		
		return dist;
	}
	
	
	public String toString() {
	
		return word+":"+count;
	}
}
