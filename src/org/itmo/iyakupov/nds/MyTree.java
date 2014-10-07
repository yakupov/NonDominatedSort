package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Stack;
import java.util.TreeMap;

public class MyTree {
	protected NavigableMap<Integer, MyTree> treeImpl = new TreeMap<Integer, MyTree>();
	public NavigableMap<Integer, MyTree> getTreeImpl() {
		return treeImpl;
	}
	
	protected boolean changed = true;
	protected boolean deleted = false;
	public boolean isDeleted() {
		if (treeImpl == null) //leaf
			return deleted;
		
		if (!changed) //Not leaf, but subtrees unchanged
			return deleted;
		else { //changed
			changed = false;
			for (Entry<Integer, MyTree> e : treeImpl.entrySet()) {
				if (!e.getValue().isDeleted())
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

	
	protected List<int[]> deleteDominated(int[] fitnesses) {
		List<int[]> dominated = new ArrayList<int[]>();
		Stack<Integer> factorStack = new Stack<Integer>();
		
		Entry<Integer, MyTree> lowerOrEq = treeImpl.floorEntry(fitnesses[0]);
		boolean wasKilled = true;
		while (wasKilled) {
			while (lowerOrEq != null && lowerOrEq.getValue().isDeleted())
				lowerOrEq = treeImpl.floorEntry(lowerOrEq.getKey());
			if (lowerOrEq != null) {
				factorStack.clear();
				factorStack.push(lowerOrEq.getKey());
				wasKilled = deleteDominated(lowerOrEq, fitnesses, factorStack, dominated);
				//TODO: process
			}
		}
		
		Entry<Integer, MyTree> upper = treeImpl.higherEntry(fitnesses[0]);
		wasKilled = true;
		while (wasKilled) {
			while (upper != null && upper.getValue().isDeleted())
				upper = treeImpl.higherEntry(upper.getKey());
			if (upper != null) {
				factorStack.clear();
				factorStack.push(upper.getKey());
				wasKilled = deleteDominated(upper, fitnesses, factorStack, dominated);
				//TODO: process
			}
		}
		
		return dominated;
	}
	
	protected boolean deleteDominated(Entry<Integer, MyTree> currPoint, 
			int[] fitnesses,
			Stack<Integer> factorStack, 
			List<int[]> results) {
		
		Stack<Integer> localStack = (Stack<Integer>) factorStack.clone();
		if (currPoint.getValue() != null) {
			Entry<Integer, MyTree> lowerOrEq = currPoint.getValue().getTreeImpl().floorEntry(fitnesses[0]);
			boolean wasKilled = true;
			while (wasKilled) {
				while (lowerOrEq != null && lowerOrEq.getValue().isDeleted())
					lowerOrEq = currPoint.getValue().getTreeImpl().floorEntry(lowerOrEq.getKey());
				if (lowerOrEq != null) {
					localStack.push(lowerOrEq.getKey());
					//TODO: unmodifiable
					wasKilled = deleteDominated(lowerOrEq, fitnesses, factorStack, results);
					if (wasKilled)
						currPoint.getValue().getTreeImpl().remove(lowerOrEq.getKey());
					localStack.pop();
				}
			}
			
			Entry<Integer, MyTree> upper = currPoint.getValue().getTreeImpl().higherEntry(fitnesses[0]);
			wasKilled = true;
			while (wasKilled) {
				while (upper != null && upper.getValue().isDeleted())
					upper = currPoint.getValue().getTreeImpl().higherEntry(upper.getKey());
				if (upper != null) {
					localStack.push(upper.getKey());
					//TODO: unmodifiable
					wasKilled = deleteDominated(upper, fitnesses, factorStack, results);
					if (wasKilled)
						currPoint.getValue().getTreeImpl().remove(upper.getKey());
					localStack.pop();
				}
			}
			
			if (currPoint.getValue().getTreeImpl().size() == 0)
				return true;
			else
				return false;
		} else { //leaf
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
				//TODO: prepare array, add to results
				//TODO !!!!!!!!
				//TODO: MyStack extends ArrayLis
				//TODO !!!!!!!!
			}
		}
		
	}
	
	
	/**
	 * Assuming that dominatesSomebody(fitnesses) == DomStatus.DOMINATES
	 * @param fitnesses - new point.
	 */
	public List<int[]> add(int[] fitnesses) {
		//return deleteDominated();
	}
	

	protected void add(Entry<Integer, MyTree> currPoint, 
			int[] newFitnesses, 
			int currFactorIndex) {
		
	}
}
