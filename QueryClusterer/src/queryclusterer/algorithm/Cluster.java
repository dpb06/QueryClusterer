package queryclusterer.algorithm;

import java.util.Set;
import java.util.TreeSet;

public class Cluster extends DistributionContainer {

	Set<Word> distsInCluster = new TreeSet<Word>();
	
	Distribution mergedDistribution = null;
	
	
	public Distribution getDistribution() {
		return mergedDistribution;
	}
	
	public Cluster addDistributionContainter(Cluster dC){
		mergeDistribution(dC);
		distsInCluster.addAll(dC.getDistsInCluster());
		return this;
	}
	
	public Cluster addDistributionContainter(Word dC){
		mergeDistribution(dC);
		distsInCluster.add(dC);
		return this;
	}
	
	public Cluster addDistributionContainter(DistributionContainer dC){
		if(dC instanceof Cluster){
			return addDistributionContainter((Cluster)dC);
		}else if(dC instanceof Word){
			return addDistributionContainter((Word)dC);
		}else
			throw new IllegalArgumentException("Please implement this method for your object: "+dC);
	}	
	
	public Set<Word> getDistsInCluster(){
		return distsInCluster;
	}
	
	public void mergeDistribution(DistributionContainer dC){
		if(mergedDistribution == null){
			mergedDistribution = dC.getDistribution();			
		}
		else{			
			Distribution d1 = mergedDistribution;
			Distribution d2 = dC.getDistribution();
			
			double[] d1_array = d1.getDistributionArray();
			double[] d2_array = d2.getDistributionArray();
			
			if (d1_array.length == d2_array.length){
				 double[] dMerged_array = new double[d1_array.length]; 
				for(int i = 0 ; i < d1_array.length ; i++){
					dMerged_array[i] = (d1_array[i] + d2_array[i]) / 2;
				}
				mergedDistribution = new Distribution(dMerged_array);
			}else{
				throw new InternalError("Cant compare if they dont have the same number "
						+ "of categories: \n"+d1_array.length+" vs "+d2_array.length );
			}			
		}
	}
	
	
	@Override
	public String toString() {	
		return  "\n"+getDistribution().getStrength()+" -> "+distsInCluster.toString()+"\n";
	}

	@Override
	public int compareTo(DistributionContainer dC) {
		if (dC instanceof Cluster) {
			Cluster c = (Cluster) dC;
			double totalThis = 0;
			for(DistributionContainer innerThis : this.getDistsInCluster()){
				totalThis += innerThis.getDistribution().getStrength();
			}
			double totalC= 0;
			for(DistributionContainer innerC : c.getDistsInCluster()){
				totalC += innerC.getDistribution().getStrength();
			}
			return (int) (totalThis -totalC);		
			
		} 
		else if (dC instanceof Word){
			Word w = (Word) dC;
			return (this.getDistsInCluster().contains(w)? 1 :0);
		}
			throw new IllegalArgumentException("Shouldnt be here");
	}
	
	
	@Override
	public int hashCode() {
		double totalThis = 0;
		for(DistributionContainer innerThis : this.getDistsInCluster()){
			totalThis += innerThis.getDistribution().getStrength();
		}
		return (int) (totalThis*3000);
	}
	
	@Override
	public boolean equals(DistributionContainer dC) {
		if (dC instanceof Cluster) {
			if (dC instanceof Cluster) {
				Cluster c = (Cluster) dC;
				double totalThis = 0;
				for(DistributionContainer innerThis : this.getDistsInCluster()){
					totalThis += innerThis.getDistribution().getStrength();
				}
				double totalC= 0;
				for(DistributionContainer innerC : c.getDistsInCluster()){
					totalC += innerC.getDistribution().getStrength();
				}
				return (totalThis ==totalC);		
				
			} 
		} 
		else if (dC instanceof Word){
			return false;
		}
		throw new IllegalArgumentException("Shouldnt be here");
	}


	
}
