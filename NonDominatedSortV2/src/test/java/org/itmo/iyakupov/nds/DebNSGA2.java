package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * RTFM: link-cut trees
 * tree contraction
 * 
 * Если таблица значений - ориентироваться на 10^9
 */
public class DebNSGA2 {
	protected static class Individual {
		Integer[] impl;
		int nDominating;
		int rank;
		List<Individual> dominated;
		
		public Individual(Integer[] fitnesses) {
			impl = fitnesses;
			nDominating = 0;
			rank = 0;
			dominated = new ArrayList<Individual>();
		}
	}
	
	protected List<Individual> pop = new ArrayList<Individual>();
	protected List<List<Individual>> fronts = new ArrayList<List<Individual>>();
	protected Random generator = new Random();
	
	public void addPoint(Integer[] fitnesses) {
		pop.add(new Individual(fitnesses));
	}

	public void sort() {
		for (int i = 0; i < pop.size(); ++i) {
			for (int j = 0; j < pop.size(); ++j) {
				if (i != j) {
					DomStatus domStatus = dominates(pop.get(i).impl, pop.get(j).impl);
					if (domStatus == DomStatus.DOMINATES) {
						pop.get(i).dominated.add(pop.get(j));
					} else if (domStatus == DomStatus.DOMINATED) {
						pop.get(i).nDominating++;
					}  
				}
			}

			//System.err.println(pop.get(i).nDominating);
			if (pop.get(i).nDominating == 0) {
				addToFront(1, pop.get(i));
				pop.get(i).rank = 0;
			}
		}
		
		int currFront = 0;
		while (currFront < fronts.size()) {
			List<Individual> newFront = new ArrayList<Individual>();
			for (Individual fromCurrFront: fronts.get(currFront)) {
				for (Individual dominatedByCurr: fromCurrFront.dominated) {
					dominatedByCurr.nDominating--;
					if (dominatedByCurr.nDominating == 0) {
						newFront.add(dominatedByCurr);
						dominatedByCurr.rank = fronts.size();
					}
				}
			}
			if (newFront.size() > 0) {
				fronts.add(newFront);
				currFront++;
			} else
				return;
		}
	}

	private void addToFront(int i, Individual individual) {
		//System.err.println("ATF " + i);
		List<Individual> workingFront = null; 
		if (i <= fronts.size()) {
			workingFront = fronts.get(i - 1);
		} else {
			workingFront = new ArrayList<Individual>();
			fronts.add(workingFront);
		}
		workingFront.add(individual);
	}

	public Result getRandomPoint() {
		int rank = generator.nextInt(fronts.size());
		//System.err.println(rank);
		//System.err.println(fronts.get(rank).size());
		int nInFront = generator.nextInt(fronts.get(rank).size());
		Integer[] res = fronts.get(rank).get(nInFront).impl;
		return new Result(rank + 1, res);
	}
	
	private DomStatus dominates(Integer[] p1, Integer[] p2) {
		boolean lt = false;
		boolean gt = false;
		for (int i = 0; i < p1.length; ++i) {
			if (p1[i] > p2[i]) {
				gt = true;
			} else if (p1[i] < p2[i]) {
				lt = true;
			}
		}					
		
		if (lt && !gt) { //p1 dominates p2
			return DomStatus.DOMINATES;
		} else if (!lt && gt) { //p1 dominates p2
			return DomStatus.DOMINATED;
		}
		
		return DomStatus.EQUALS;
	}
	
	
	public void validate() {
		for (int i = 0; i < fronts.size(); ++i) {
			for (Individual cInd: fronts.get(i)) {
				int cRankCalcd = 0;
				for (Individual compInd: pop) {
					if (compInd != cInd) {
						if (dominates(compInd.impl, cInd.impl) == DomStatus.DOMINATES) {
							cRankCalcd = Math.max(cRankCalcd, compInd.rank + 1);
						}
					}
				}
				if (cRankCalcd != i) {
					throw new RuntimeException("Population is sorted incorrectly");
				}
			}
		}
	}
}
