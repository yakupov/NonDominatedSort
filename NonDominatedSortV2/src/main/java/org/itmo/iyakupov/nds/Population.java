package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.itmo.iyakupov.nds.Treap.Treaps;

public class Population {
	protected Set<Int2DIndividual> individuals = new HashSet<Int2DIndividual>();
	protected List<Treap> ranks = new ArrayList<Treap>();
	protected Random random = new Random();
	
	protected int determineRank(Int2DIndividual nInd) {
		int currRank = 0;
		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).dominatedBySomebody(nInd))
				currRank = i + 1;
			else
				return currRank;
		}
		return currRank;
	}
	
	public void addPoint(Int2DIndividual nInd) {
		if (individuals.contains(nInd)) {
			return;
		}
		int rank = determineRank(nInd);
		Treap nTreap = new Treap(nInd, random.nextInt(), null, null);
		if (rank >= ranks.size()) {
			ranks.add(nTreap);
		} else {
			int i = 0;
			Int2DIndividual minP = nInd;
			Treap cNext = nTreap;
			while (minP != null) {
				if (ranks.size() >= rank + i) {
					ranks.add(cNext);
					break;
				}
				Treaps t1 = ranks.get(rank + i).splitX(minP);
				Treaps tr = t1.r.splitY(minP);
				Treap res = Treap.merge(t1.l, cNext);
				res = Treap.merge(res, tr.r);
				cNext = tr.l;
				minP = cNext.getMinP();
			}
		}
	}
	
	public static class IndWithRank {
		Int2DIndividual ind;
		int rank;
		public IndWithRank(Int2DIndividual ind, int rank) {
			super();
			this.ind = ind;
			this.rank = rank;
		}
	}
	
	public IndWithRank getRandWithRank() {
		Int2DIndividual randInd = (Int2DIndividual) individuals.toArray()[random.nextInt(individuals.size())];
		int r = ranks.size();
		int l = 0;
		while (l != r) {
			int curr = (l + r)/2;
			boolean dominated = ranks.get(curr).dominatedBySomebody(randInd);
			boolean dominates = ranks.get(curr).dominatesSomebody(randInd);
			if (!dominates && !dominated)
				return new IndWithRank(randInd, curr);
			else if (dominates)
				r = curr;
			else 
				l = curr;
		}
		throw new RuntimeException("Can't determine rank for " + randInd.toString());
	}
	
	
}
