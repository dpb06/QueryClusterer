package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Defenestrayte
 * 
 *         Construction of Word Read First token of line, -> query -> -> for
 *         each query expand
 * 
 */
public class Word extends DistributionContainer {
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

	public Word(String word) {
		this.word = word;
		this.id = WORDS.size(); // first is zero,
		WORDS.add(this);
		Word.totalCount++;

	}

	private class SortById implements Comparator<Word> {

		public int compare(Word o1, Word o2) {
			// TODO Auto-generated method stub
			return o2.getId() - o1.getId();
		}
	}

	public String getWord() {
		return word;
	}

	// called by query.mergedDist // for test
	public Distribution getDistribution(Query query, List<Category> categories) {

		// dist = new Distribution(this, categories, query);
		dist = testDistribution(query, categories);
		return dist;
	}

	// Test algorithm // called by this.getDist
	public Distribution testDistribution(Query query, List<Category> categories) {
		if (dist != null) {
			return dist;
		}

		double[] probArray = new double[categories.size()];

		for (Category category : categories) {
			double bias = 0.0;
			double a = 0.007; //constant
			double b = 0.010; // * random
			double c = 0.00003;   // * bias (if cat)
			double d = 0.00125;  // + bias (if cat)
			if (query.getVotedCategories().contains(category)) {
				bias = c*(Math.random())+d;
			}
			
			double measure = (b*Math.random() - b/2)+(a) + (count)* bias ; // cant be zero

			// System.out.println("CategoryPos"+category.getCategoryId()+"/"+Category.getNumberOfCategories());
			// System.out.println(Arrays.toString(probArray));

			probArray[category.getCategoryId()] = measure;

			// System.out.println(Arrays.toString(probArray));
			// System.out.println();
		}

		dist = new Distribution(probArray);
		return dist;
	}

	public Cluster addDistributionContainter(Cluster dC) {
		return dC.addDistributionContainter(this);
	}

	public Cluster addDistributionContainter(Word dC) {
		Cluster c = new Cluster();
		c.addDistributionContainter(dC);
		c.addDistributionContainter(this);
		return c;
	}

	public Cluster addDistributionContainter(DistributionContainer dC) {
		if (dC instanceof Cluster) {
			return addDistributionContainter((Cluster) dC);
		} else if (dC instanceof Word) {
			return addDistributionContainter((Word) dC);
		} else
			throw new IllegalArgumentException(
					"Please implement this method for your object: " + dC);
	}

	public int getCount() {
		return count;
	}

	public static Word getWord(String _word) {

		for (Word existWord : WORDS) {
			if (existWord.getWord().equals(_word)) {

				existWord.count++;
				Word.totalCount++;
				System.out.println(existWord.count + "/" + Word.totalCount);
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

//		return getDistribution().getStrength()+" -> "+word ;
		return word ;
	}
	
	@Override
	public boolean equals(DistributionContainer dC) {
		if (dC instanceof Word) {
			Word w = (Word) dC;
			return this.getWord().equals(w.getWord());
		} 
		else if (dC instanceof Cluster){
			return false;
		}
		throw new IllegalArgumentException("Shouldnt be here");
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getWord().hashCode();
	}

	@Override
	public int compareTo(DistributionContainer dC) {
		if (dC instanceof Word) {
			Word w = (Word) dC;
			return this.getWord().compareTo(w.getWord());
		} 
		else if (dC instanceof Cluster){
			return (((Cluster) dC).getDistsInCluster().contains(this)? 1 :0);
		}
		throw new IllegalArgumentException("Shouldnt be here");
	}
}
