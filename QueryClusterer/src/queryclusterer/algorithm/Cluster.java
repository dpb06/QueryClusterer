package queryclusterer.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cluster implements DistributionContainer {

	List<DistributionContainer> distsInCluster = new ArrayList<DistributionContainer>();
	
	Distribution mergedDistribution = null;
	
	
	public Distribution getDistribution() {
		return mergedDistribution;
	}
	
	public void addDistributionContainter(DistributionContainer dC){
		distsInCluster.add(dC);
		mergeDistribution(dC);
		
	}
	
	private void mergeDistribution(DistributionContainer dC){
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

		
	
//		return "------------------------"
//				+ "\nCluster"
//				+ "\n\tDistribution:\n"+mergedDistribution
				return ""+ "\n\t\n["+distsInCluster.toString()+"]\n";
	}
	
}
