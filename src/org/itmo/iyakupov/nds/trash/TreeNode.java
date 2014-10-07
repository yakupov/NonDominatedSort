package org.itmo.iyakupov.nds.trash;

import org.itmo.iyakupov.nds.DomStatus;

public class TreeNode {
	public TreeNode(int[] fitnesses) {
		this(fitnesses, 0);
	}
	
	protected TreeNode(int[] fitnesses, int offset) {
		dim = fitnesses.length - offset;
		val = fitnesses[offset];
		if (dim > 1) {
			sub = new TreeNode(fitnesses, offset + 1);
		}
	}	
	private int dim; //unused maybe
	public TreeNode l, r;
	public TreeNode sub;
	public int val;
	
	public DomStatus checkPoint(int[] fitnesses) {
		return checkPoint(fitnesses, 0, 0);
	}
	
	protected final int GT=0x01;
	protected final int LT=0x10;
	protected DomStatus checkPoint(int[] fitnesses, int offset, int mask) {
		int nmask = mask;
		if (fitnesses[offset] < val) {
			nmask |= LT;
		} else if (fitnesses[offset] > val) {
			nmask |= GT;
		}
		
		if (sub != null) {
			return sub.checkPoint(fitnesses, offset + 1, nmask);
		} else {
			if ((nmask & GT) > 0 && (nmask & LT) > 0) {
				return DomStatus.EQUALS;
			} else if ((nmask & GT) > 0) {
				return DomStatus.DOMINATED;
			} else if ((nmask & LT) > 0) {
				return DomStatus.DOMINATES;
			} else {
				return DomStatus.EXISTS;
			}
		}
	}
	
	public void addPointIfEq(int[] fitnesses, int offset) {
		if ((offset == 0) && (checkPoint(fitnesses) != DomStatus.EQUALS)) { //check myself
			throw new RuntimeException("Invalid domination status when adding an 'eq' point");
		}
		
		if (fitnesses[offset] == val) {
			addPointIfEq(fitnesses, offset + 1);
		} else if (fitnesses[offset] < val) {
			if (l != null) {
				l.addPointIfEq(fitnesses, offset);
			} else {
				l = new TreeNode(fitnesses, offset);
			}
		} else if (fitnesses[offset] > val) {
			if (r != null) {
				r.addPointIfEq(fitnesses, offset);
			} else {
				r = new TreeNode(fitnesses, offset);
			}
		}
	}
}
