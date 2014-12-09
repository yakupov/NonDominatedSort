package org.itmo.iyakupov.nds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrayOfTrees {
	protected final boolean debug = true;
	
	protected List<MyTree> ranks = new ArrayList<MyTree>();
	protected int lastCalculatedRank = 0; // index in list
	protected Random generator = new Random();

	public Result getRandomPoint() {
		int rank = generator.nextInt(ranks.size());
		for (int i = lastCalculatedRank + 1; i <= rank; ++i) {
			addPending(i + 1, ranks.get(i).processPending());
			lastCalculatedRank = i;
		}
		Integer[] res = ranks.get(rank).getRandPoint(null);
		return new Result(rank + 1, res);
	}
	
	public int addPoint(Integer[] fitnesses) {
		return addPointBlob(fitnesses); 
	}
	
	//protected int addPointBS(Integer[] fitnesses, int start, int finish);
	//TODO - start with o(nranks)
	
	@Deprecated
	protected int addPointBlob(Integer[] fitnesses) {
		for (int i = 0; i < ranks.size(); ++i) {
			if (i > lastCalculatedRank) {
				addPending(i + 1, ranks.get(i).processPending());
				lastCalculatedRank = i;
			}
				
			DomStatus domStatus = ranks.get(i).dominatesSomebody(fitnesses);
			if (domStatus == DomStatus.EXISTS)
				return i + 1;
			else if (domStatus != DomStatus.DOMINATED) {
				List<Integer[]> pending = ranks.get(i).add(fitnesses);
				
				if (debug) {
					System.out.println("Adding non-dominated point:" + Arrays.toString(fitnesses));
					if (pending != null && !pending.isEmpty()) {
						System.out.println("it has dominated:");
						for (Integer[] dead : pending) {
							System.out.println(Arrays.toString(dead));
						}
					}
				}
								
				addPending(i + 1, pending);
				return i + 1;
			}
		}
		
		//create new rank
		MyTree newRank = new MyTree();
		newRank.add(fitnesses);
		lastCalculatedRank = ranks.size();
		ranks.add(newRank);
		return ranks.size();
	}

	protected void addPending(int i, List<Integer[]> pending) {
		if (pending == null || pending.size() == 0)
			return;
		
		if (i < ranks.size()) {
			ranks.get(i).setPending(pending);
			lastCalculatedRank = i;
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
