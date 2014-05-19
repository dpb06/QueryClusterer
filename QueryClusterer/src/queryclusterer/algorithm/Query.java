package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Query {

	
	private List<Word> queryWords = new ArrayList<Word>();
	
	private String query;
	
	private List<Category> votedCategories = new ArrayList<Category>();
	
	private Distribution distribution;

	private double[][] results;
	
	public Query(String query){
		this.query = query;		
	}


	public Distribution getDistribution(int M) {
		List<DistributionContainer> distributionContainers = new ArrayList<DistributionContainer>();
		distributionContainers.addAll(queryWords);
		if (distribution == null)
			distribution = mergedDistribution(distributionContainers, M);
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
	private Distribution mergedDistribution(List<DistributionContainer> distributionsInPlay, int M) {
		
		ensureDistributionsInWords(distributionsInPlay);
		//Sort by strength for later
		Comparator<DistributionContainer> distComparator = new Comparator<DistributionContainer>() {
			public int compare(DistributionContainer o1, DistributionContainer o2) {
				return (int)(5000*(o2.getDistribution().getStrength() - o1.getDistribution().getStrength()));
			}
		};
		
		
		
		Collections.sort(distributionsInPlay, distComparator);
		
		
		List<DistributionContainer> distributionsInClusters = new ArrayList<DistributionContainer>(M);
		//get the strongest M distributions
		for(int i = 0 ; i < M ; i++){
			distributionsInClusters.add(distributionsInPlay.remove(distributionsInPlay.size()-1));
		}
		
		
		
		
		System.out.println("-----------=== KL for "+query+" starting");
		KullbeckLiebler KL = new KullbeckLiebler();
		
		System.out.println("----------------> Words Now all have Dists");
		
		//create results as the max size it can possibly be
		if(results == null){
			results = new double[distributionsInPlay.size()][distributionsInPlay.size()];
			for(int x = 0 ; x < results.length ; x++)
				for(int y = 0 ; y < results.length ; y++)
					results[x][y] = -2; // denotes empty 
		}
		
		
		for(int i = 0 ; i < distributionsInClusters.size() ; i++){
			DistributionContainer s_word = distributionsInClusters.get(i);
			for(int j = 0 ; j < distributionsInClusters.size(); j++){
				DistributionContainer t_word = distributionsInClusters.get(j);
				if(i != j){ // is empty, hasnt been updated so fill it in
						results[i][j] = KL.weightedAverageKL(s_word, t_word); //returns symmetrical measurement of "distance"
						results[j][i] = results[i][j];
						//ensuring middle row empty
						results[i][i] = -2; 
						results[j][j] = -2;
						System.out.println(results[i][j]);
						
				}
			}
		}
		

		// now have all distances between different words for the query
		while(!distributionsInPlay.isEmpty()){			
			
			int previous_lowest_i = -1;
			int previous_lowest_j = -1;
			double lowestDifference = Double.MAX_VALUE;
			
			for(int j = 0 ; j < distributionsInClusters.size() ; j++ ){
				for(int i = 0; i < distributionsInClusters.size(); i++ ){ // i is always less than j
					if(i != j){
						if(results[i][j] < lowestDifference && results[i][j] != -2){
							previous_lowest_i = i;
							previous_lowest_j = j;
							lowestDifference = results[i][j];								
						}
					}
				}			
			}
			
			// j is further on in the list, we will replace it with the value 
			DistributionContainer j_dist = distributionsInClusters.get(previous_lowest_j);  
			DistributionContainer i_dist = distributionsInClusters.get(previous_lowest_i); // i is still in the same place, now we remove
			
			Cluster merged = new Cluster();
			merged.addDistributionContainter(i_dist);
			merged.addDistributionContainter(j_dist);
			
			distributionsInClusters.add(merged);	
			
			// need to move values of the last dist (size-1) in the list 
			//   into where "previous_lowest_i" was, deleting as you go
			for(int x=0 ; x < distributionsInClusters.size() ; x++){
			   results[previous_lowest_i][x] = results[distributionsInClusters.size()-1][x];
			   results[distributionsInClusters.size()-1][x] = -2; // deleted
			
			   results[x][previous_lowest_i] = results[x][distributionsInClusters.size()-1];
			   results[x][distributionsInClusters.size()-1] = -2; // deleted
			}
			   distributionsInClusters.set(previous_lowest_i,
					   distributionsInClusters.remove(distributionsInClusters.size()-1));
			
			// then put the values of the merged cluster into where "previous_lowest_j" was
			// 
			// work out all matches with it
			//   results[x][
			
			// work out all for 
		
		
		}
		Collections.sort(distributionsInClusters, distComparator);
		
		// the last one in the list should hold the strongest candidate Distribution
		return distributionsInClusters.get(distributionsInClusters.size()-1).getDistribution();
	}
	
	private void ensureDistributionsInWords(List<DistributionContainer> queryWords){
		for(DistributionContainer word : queryWords){
			if(word instanceof Word)
				((Word)word).getDistribution(this, votedCategories);
		}  
	}

	public List<Category> getVotedCategories() {

		return votedCategories;
	}
	
	
	
	
	
	
}
