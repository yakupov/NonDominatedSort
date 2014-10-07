package org.itmo.iyakupov.nds;

import java.util.ArrayList;

import org.itmo.iyakupov.nds.trash.TreeNode;

public class ArrayOfTrees {
	protected ArrayList<TreeNode> ranks = new ArrayList<TreeNode>();
	
	public TreeNode get(int rank) {
		if (rank < 1)
			throw new IllegalArgumentException("No rank " + rank);
		if (ranks.size() < rank) {
			return null;
		}
		return ranks.get(rank - 1);
	}
	
	public int addPoint(int[] fitnesses) {
		return addPointBlob(fitnesses); 
	}
	
	//protected int addPointBS(int fitnesses, int start, int finish);
	//TODO - start with o(nranks)
	
	@Deprecated
	protected int addPointBlob(int[] fitnesses) {
		for (int i = 0; i < ranks.size(); ++i) {
			DomStatus dominates = ranks.get(i).checkPoint(fitnesses); 
		}
	}
}
