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
		} else {
			/*
			for (Int2DIndividual ii: individuals) {
				System.err.println("popz incl " + ii + " eq " + nInd.equals(ii));
			}
			System.err.println("popz add " + nInd);
			*/
			individuals.add(nInd);
		}
		int rank = determineRank(nInd);
		System.err.println(rank + "_" + nInd.toString() + "_" + ranks.size());
		Treap nTreap = new Treap(nInd, random.nextInt(), null, null);
		if (rank >= ranks.size()) {
			ranks.add(nTreap);
		} else {
			int i = 0;
			Int2DIndividual minP = nInd;
			Treap cNext = nTreap;
			while (minP != null) {
				if (ranks.size() <= rank + i) {
					ranks.add(cNext);
					break;
				}
				
				boolean printTreaps = true;
				printTreap(cNext, printTreaps);
				printTreap(ranks.get(rank + i), printTreaps);

				Treaps t1 = ranks.get(rank + i).splitX(minP);
				Treaps tr = new Treaps();
				if (t1.r != null)
					tr = t1.r.splitY(minP);
				
				printTreap(t1.l, printTreaps);
				printTreap(t1.r, printTreaps);
				printTreap(tr.l, printTreaps);
				printTreap(tr.r, printTreaps);
				
				Treap res = Treap.merge(t1.l, cNext);
				printTreap(res, printTreaps);
				res = Treap.merge(res, tr.r);
				printTreap(res, printTreaps);
				ranks.set(rank + i, res);
				cNext = tr.l;
				printTreap(cNext, printTreaps);
				
				if (cNext == null)
					break;
				minP = cNext.getMinP();
				i++;
			}
		}
	}

	private void printTreap(Treap cNext, boolean sw) {
		if (sw)
			System.err.println(cNext);
	}
	
	public static class IndWithRank {
		Int2DIndividual ind;
		int rank;
		public IndWithRank(Int2DIndividual ind, int rank) {
			super();
			this.ind = ind;
			this.rank = rank;
		}
		
		public String toString() {
			return "Rank = " + rank + ", value = " + String.valueOf(ind);
		}
	}
	
	public IndWithRank getRandWithRank() {
		if (individuals.size() == 0)
			throw new RuntimeException("Can't get random individual from empty population");
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ranks.size(); ++i) {
			sb.append(i);
			sb.append('\n');
			sb.append(ranks.get(i).toString());
			sb.append('\n');
		}
		return sb.toString();
	}
}
