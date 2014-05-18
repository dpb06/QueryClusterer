package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Query implements DistributionContainer {

	
	private List<Word> queryWords = new ArrayList<Word>();
	
	private String query;
	
	private List<Category> votedCategories = new ArrayList<Category>();
	
	private Distribution distribution;
	
	public Query(String query){
		this.query = query;		
	}

	@Override
	public Distribution getDistribution() {
		if (distribution == null)
			distribution = mergedDistribution(queryWords);
		return distribution;
	}

	
	public void addVotedCategory(Category cat){
		votedCategories.add(cat);
	}
	
	public void addQueryWord(Word word){
		queryWords.add(word);
	}
	
	public void addQueryWords(List<Word> words){
		queryWords = words;
	}
	
	public String getQuery(){
		return query;
	}
	
	//KL algorithm
	private Distribution mergedDistribution(List<Word> queryWords) {
		System.out.println("-----------=== KL for "+query+" starting");
		KullbeckLiebler KL = new KullbeckLiebler();
		ensureDistributionsInWords(queryWords);
		System.out.println("----------------> Words Now all have Dists");
		double[][] results = new double[queryWords.size()][queryWords.size()];
		for(int i = 0 ; i < queryWords.size() ; i++){
			Word s_word = queryWords.get(i);
			for(int j = 0 ; j < queryWords.size(); j++){
				Word t_word = queryWords.get(j);
				if(i != j){
					results[i][j] = KL.weightedAverageKL(s_word, t_word); //returns symmetrical measurement of "distance"
					results[j][i] = results[i][j];
					//ensuring middle row empty
					results[i][i] = 0;
					results[j][j] = 0;
					System.out.println("I:" + i + " -> "+ s_word +" ,J: "+ j + " -> "+ t_word);
				}
			}
		}
		// now have all distances between different words for the query
		
		
		
		
		
		return null;
	}
	
	private void ensureDistributionsInWords(List<Word> queryWords){
		for(Word word : queryWords){
			word.getDistribution(this, votedCategories);
		}  
	}

	public List<Category> getVotedCategories() {

		return votedCategories;
	}
	
	
	
	
	
	
}
