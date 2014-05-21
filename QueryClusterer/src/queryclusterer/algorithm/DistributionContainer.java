package queryclusterer.algorithm;

public abstract class DistributionContainer implements Comparable<DistributionContainer> {

	public abstract Distribution getDistribution();

	public boolean equals(DistributionContainer dC) {
		return super.equals(dC);
	}
	

}
