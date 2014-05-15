package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Query implements DistributionContainer {

	
	private List<Word> queryWords = new ArrayList<Word>();
	
	private String query;

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

	
	//KL algorithm
	private Distribution mergedDistribution(List<Word> queryWords) {
		
		return null;
	}
	
	
	
	
	
	
}
