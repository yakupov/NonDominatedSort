package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.itmo.iyakupov.nds.util.MyStack;

public class MyTree {
	protected NavigableMap<Integer, MyTree> treeImpl;
	
	public MyTree() {
		treeImpl = new TreeMap<Integer, MyTree>();
	}
	
	public NavigableMap<Integer, MyTree> getTreeImpl() {
		return treeImpl;
	}
	
	protected boolean changed = true;
	protected boolean deleted = false;
	public boolean isDeleted() {
		if (!changed) //subtrees unchanged
			return deleted;
		else { //changed
			changed = false;
			for (Entry<Integer, MyTree> e : treeImpl.entrySet()) {
				if (e.getValue() == null) //leaf
					return deleted; 
				else if (!e.getValue().isDeleted()) //not leaf, exists non-deleted child
					return deleted = false;
			}
			return deleted = true;
		}
	}
	
	public DomStatus dominatesSomebody(int[] fitnesses) {		
		Entry<Integer, MyTree> lowerOrEq = treeImpl.floorEntry(fitnesses[0]);
		while (lowerOrEq != null && lowerOrEq.getValue().isDeleted())
			lowerOrEq = treeImpl.floorEntry(lowerOrEq.getKey());
		
		Entry<Integer, MyTree> upper = treeImpl.higherEntry(fitnesses[0]);
		while (upper != null && upper.getValue().isDeleted())
			upper = treeImpl.higherEntry(upper.getKey());
		
		if (lowerOrEq == null && upper == null) {
			return DomStatus.DOMINATES;
		} else if (lowerOrEq == null) {
			return checkPoint(upper, fitnesses, 0, false, false);
		}
		
		//Lower not null
		DomStatus checkResults = checkPoint(lowerOrEq, fitnesses, 0, false, false);
		if (checkResults != DomStatus.EQUALS || upper == null)
			return checkResults;
		
		//Not come here if upper is null
		return checkPoint(upper, fitnesses, 0, false, false);
	}

	/**
	 * 
	 * @param currComparedPoint - existing point
	 * @param newFitnesses - new point
	 * @param currFactorIndex
	 * @param lt - some of previous factors was less in new point than in the existing
	 * @param gt - some of previous factors was greater in new point than in the existing
	 * @return
	 */
	protected DomStatus checkPoint(Entry<Integer, MyTree> currComparedPoint, 
			int[] newFitnesses, 
			int currFactorIndex, 
			boolean lt, boolean gt) {
		if (newFitnesses[currFactorIndex] < currComparedPoint.getKey()) {
			lt = true;
		} else if (newFitnesses[currFactorIndex] > currComparedPoint.getKey()) {
			gt = true;
		}

		if (currComparedPoint.getValue() != null) {
			Entry<Integer, MyTree> lowerOrEq = currComparedPoint.getValue().getTreeImpl().floorEntry(newFitnesses[0]);
			while (lowerOrEq != null && lowerOrEq.getValue().isDeleted())
				lowerOrEq = currComparedPoint.getValue().getTreeImpl().floorEntry(lowerOrEq.getKey());
			
			Entry<Integer, MyTree> upper = currComparedPoint.getValue().getTreeImpl().higherEntry(newFitnesses[0]);
			while (upper != null && upper.getValue().isDeleted())
				upper = currComparedPoint.getValue().getTreeImpl().higherEntry(upper.getKey());
			
			if (lowerOrEq != null || upper != null) {
				if (lowerOrEq == null) {
					return checkPoint(upper, newFitnesses, currFactorIndex + 1, lt, gt);
				}
				
				//Lower not null
				DomStatus checkResults = checkPoint(lowerOrEq, newFitnesses, currFactorIndex + 1, lt, gt);
				if (checkResults != DomStatus.EQUALS || upper == null)
					return checkResults;
				
				//Not come here if upper is null
				return checkPoint(upper, newFitnesses, currFactorIndex + 1, lt, gt);
			}
		}

		if (lt && gt) {
			return DomStatus.EQUALS;
		} else if (gt) {
			return DomStatus.DOMINATED;
		} else if (lt) {
			return DomStatus.DOMINATES;
		} else {
			return DomStatus.EXISTS;
		}
	}

	protected List<Integer[]> deleteDominated(Integer[] fitnesses) {
		List<Integer[]> dominated = new ArrayList<Integer[]>();
		MyStack<Integer> factorStack = new MyStack<Integer>();
		
		deleteDominated(fitnesses, factorStack, dominated);
		return dominated;
	}
	
	protected boolean deleteDominated(Integer[] fitnesses,
			List<Integer> factorStack,
			List<Integer[]> results) {
		changed = true;
		MyStack<Integer> localStack = new MyStack<Integer>(factorStack);

		System.err.println("--------- exec dd recursive, stack = " + Arrays.toString(factorStack.toArray()));
		
		boolean last = (factorStack.size() == fitnesses.length - 1); //FIXME maybe
		//Assert that values in tree are null

		Entry<Integer, MyTree> lowerOrEq = treeImpl.floorEntry(fitnesses[factorStack.size()]);
		boolean wasKilled = true;
		while (wasKilled) {
			wasKilled = false;
			while (lowerOrEq != null && lowerOrEq.getValue() != null && lowerOrEq.getValue().isDeleted())
				lowerOrEq = treeImpl.floorEntry(lowerOrEq.getKey());
			if (lowerOrEq != null) {
				localStack.push(lowerOrEq.getKey());
				if (!last)
					wasKilled = lowerOrEq.getValue().deleteDominated(fitnesses, localStack, results);
				else 
					wasKilled = tryToKillLast(fitnesses, localStack, results);				
				if (wasKilled) {
					treeImpl.remove(lowerOrEq.getKey()); //TODO: maybe lazy
					lowerOrEq = treeImpl.higherEntry(fitnesses[factorStack.size()]);
				}
				localStack.pop();
			}
		}
		
		Entry<Integer, MyTree> upper = treeImpl.higherEntry(fitnesses[factorStack.size()]);
		wasKilled = true;
		while (wasKilled) {
			wasKilled = false;
			while (upper != null && upper.getValue() != null && upper.getValue().isDeleted())
				upper = treeImpl.higherEntry(upper.getKey());
			if (upper != null) {
				localStack.push(upper.getKey());
				if (!last)
					wasKilled = upper.getValue().deleteDominated(fitnesses, localStack, results);
				else 
					wasKilled = tryToKillLast(fitnesses, localStack, results);
				if (wasKilled) {
					treeImpl.remove(upper.getKey()); //TODO: maybe lazy
					upper = treeImpl.higherEntry(fitnesses[factorStack.size()]);
				}
				localStack.pop();
			}
		}

		if (getTreeImpl().size() == 0)
			return true;
		else
			return false;
	}
	
	protected boolean tryToKillLast(Integer[] fitnesses,
			MyStack<Integer> factorStack,
			List<Integer[]> results) {
		assert(factorStack.size() == fitnesses.length);
		System.err.println("------ try to kill last, stack = " + Arrays.toString(factorStack.toArray()));
		
		boolean lt = false;
		boolean gt = false;
		for (int i = 0; i < factorStack.size(); ++i) {
			if (fitnesses[i] > factorStack.get(i)) {
				gt = true;
			} else if (fitnesses[i] < factorStack.get(i)) {
				lt = true;
			}
		}					
		
		if (lt && !gt) { //domination
			Integer[] currResult = factorStack.toArray(new Integer[factorStack.size()]);
			results.add(currResult);
			deleted = true;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Assuming that dominatesSomebody(fitnesses) == DomStatus.DOMINATES or it's first level
	 * @param fitnesses - new point.
	 */
	public List<Integer[]> add(Integer[] fitnesses) {
		List<Integer[]> dominated = deleteDominated(fitnesses);
		System.err.println("------- dominated deleted - OK");
		add(fitnesses, 0);
		return dominated;
	}
	
	protected void add(Integer[] newFitnesses, int currFactorIndex) {
		changed = true;
		boolean last = (currFactorIndex == newFitnesses.length - 1);
		if (last) {
			if (!treeImpl.keySet().contains(newFitnesses[currFactorIndex])) {
				treeImpl.put(newFitnesses[currFactorIndex], null);
			}
		} else {
			if (treeImpl.keySet().contains(newFitnesses[currFactorIndex])) {
				treeImpl.get(newFitnesses[currFactorIndex]).add(newFitnesses, currFactorIndex + 1);
			} else {
				MyTree newTree = new MyTree();
				treeImpl.put(newFitnesses[currFactorIndex], newTree);
				newTree.add(newFitnesses, currFactorIndex + 1);
			}
		}
	}
}
