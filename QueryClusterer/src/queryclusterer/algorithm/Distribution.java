package queryclusterer.algorithm;

import java.util.List;

public class Distribution {

	private static double[] probForCategories; 
	
	public Distribution(Word word, List<Category> categories){
		
		int numCats = Category.getNumberOfCategories();
		probForCategories = new double[numCats];
		for(Category cat : categories){
			
		}
		System.out.println("Dist created: "+word);
	}
	
	public Distribution(double[] distArray){
		probForCategories = distArray;
	}
	
	public double[] getDistributionArray(){
		return probForCategories;
	}
	
	
	public String toString() {
	
		return " Dist-> "+probForCategories.toString();
	}
}
