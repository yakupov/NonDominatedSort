package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.List;

import org.itmo.iyakupov.nds.trash.TreeNode;

public class ArrayOfTrees {
	protected ArrayList<MyTree> ranks = new ArrayList<MyTree>();
	protected int lastCalculatedRank;
	
	public MyTree get(int rank) {
		if (rank < 1)
			throw new IllegalArgumentException("No rank " + rank);
		if (ranks.size() < rank) {
			return null;
		}
		return ranks.get(rank - 1);
	}
	
	public int addPoint(Integer[] fitnesses) {
		return addPointBlob(fitnesses); 
	}
	
	//protected int addPointBS(Integer[] fitnesses, int start, int finish);
	//TODO - start with o(nranks)
	
	@Deprecated
	protected int addPointBlob(Integer[] fitnesses) {
		for (int i = 0; i < ranks.size(); ++i) {
			DomStatus dominates = ranks.get(i).dominatesSomebody(fitnesses);
			if (dominates == DomStatus.DOMINATES) {
				List<Integer[]> pending = ranks.get(i).add(fitnesses);
				addPending(i + 1, pending);
				return i + 1;
			}
		}
		
		//create new rank
		MyTree newRank = new MyTree();
		newRank.add(fitnesses);
		ranks.add(newRank);
		return ranks.size();
	}

	private void addPending(int i, List<Integer[]> pending) {
		if (pending.size() == 0)
			return;
		
		if (i < ranks.size()) {
			ranks.get(i).setPending(pending);
		} else {
			MyTree newRank = new MyTree();
			for (Integer[] fitnesses: pending) {
				List<Integer[]> subPending = newRank.add(fitnesses);
				assert (subPending == null || subPending.size() == 0);
			}
			ranks.add(newRank);
		}
	}
}
