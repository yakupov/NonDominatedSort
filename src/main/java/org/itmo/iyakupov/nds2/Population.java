package org.itmo.iyakupov.nds2;

import java.util.ArrayList;
import java.util.List;

public class Population {
	protected List<Individual> individuals = new ArrayList<Individual>();
	protected final int dimension;
	
	public Population (int dimension) {
		this.dimension = dimension;
	}
	
	
	public void add(Integer[] newValue) {
		if (newValue.length != dimension)
			throw new IllegalArgumentException("Can't add point because it's not comparable with the population");
		
		int maxDominatingRg = 0;
		List<Individual> dominated = new ArrayList<Individual>();
		List<Individual> dominating = new ArrayList<Individual>();
		
		for (Individual exPoint: individuals) {
			ComparsionStatus cs = comparePoints(newValue, exPoint.getValue());
			if (cs == ComparsionStatus.EQ) {
				return;
			} else if (cs == ComparsionStatus.DOMINATED) {
				maxDominatingRg = Math.max(maxDominatingRg, exPoint.getRg());
				dominating.add(exPoint);
			} else if (cs == ComparsionStatus.DOMINATING) {
				dominated.add(exPoint);
			}
		}
		
		Individual newInd = new Individual(newValue, maxDominatingRg + 1);
		individuals.add(newInd);
		
		//TODO: increase RGs of dominated points
	}
	
	/**
	 * p1 dominates p2?
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected ComparsionStatus comparePoints(Integer[] p1, Integer[] p2) {
		if (p1 == null || p2 == null)
			return ComparsionStatus.UNCOMPARABLE;
		if (p1.length != p2.length)
			return ComparsionStatus.UNCOMPARABLE;
		
		boolean gt = false;
		boolean lt = false;
		for (int i = 0; i < p1.length; ++i) {
			if (p1[i] < p2[i])
				lt = true;
			else if (p1[i] > p2[i])
				gt = true;
		}
		
		if (!gt && !lt)
			return ComparsionStatus.EQ;
		else if (gt && !lt)
			return ComparsionStatus.DOMINATING;
		else if (!gt && lt)
			return ComparsionStatus.DOMINATED;
		else
			return ComparsionStatus.UNCOMPARABLE;
	}
}
