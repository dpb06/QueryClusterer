package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Word implements DistributionContainer {
	
	private static List<Word> WORDS = new ArrayList<Word>();
	private int id = -1;
	private Distribution

	
	private int getId() {
		
		return id;
	}
	
	
	
	private class SortById implements Comparator<Word>{


		public int compare(Word o1, Word o2) {
			// TODO Auto-generated method stub
			return o2.getId() - o1.getId();
		}

		
		
	}



	@Override
	public Distribution getDistribution() {
		// TODO Auto-generated method stub
		return null;
	}
}
