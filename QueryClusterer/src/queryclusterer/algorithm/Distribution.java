package queryclusterer.algorithm;

import java.util.Arrays;
import java.util.List;

public class Distribution {

	private double[] probForCategories; 
	private double strength = 0;
	
	public Distribution(Word word, List<Category> categories){
		
		int numCats = Category.getNumberOfCategories();
		probForCategories = new double[numCats];
		for(Category cat : categories){
			
		}
		System.out.println("Dist created: "+word);
	}
	
	public Distribution(double[] distArray){
		probForCategories = distArray;
		for(int i = 0 ; i < probForCategories.length ; i++)
			strength += probForCategories[i];
		strength /= probForCategories.length;
		//strength is the average probability at the mo
	}
	
	public double[] getDistributionArray(){
		return probForCategories;
	}
	
	
	public String toString() {
	
		return " Dist-> "+Arrays.toString(probForCategories);
	}
	
	
	public double getStrength(){
		return strength;
	}
	
}
