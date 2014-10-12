package org.itmo.iyakupov.nds;

import java.util.Arrays;
import java.util.List;

import org.itmo.iyakupov.nds.ArrayOfTrees.Result;

public class Tester {

	public static void main(String[] args) {
		MyTree mt = new MyTree();
		ArrayOfTrees aot = new ArrayOfTrees();
		
		Integer[][] testData = new Integer[][]{new Integer[]{4, 4}, new Integer[]{2, 6}, 
				new Integer[]{1, 8}, new Integer[]{2, 2}, new Integer[]{2, 1}, new Integer[]{5, 5}};
		
		for (Integer[] toAdd : testData) {
			test2(aot, toAdd);
			printRandomPoint(aot);
			//addAndPrintDominated(mt, toAdd);
		}
	}

	private static void printRandomPoint(ArrayOfTrees aot) {
		Result r = aot.getRandomPoint();
		System.out.printf("Rank: %d, point: %s\n", r.rank, Arrays.toString(r.point));
	}

	public static void addAndPrintDominated(MyTree mt, Integer[] point) {
		System.out.println("Adding point:" + Arrays.toString(point));
		System.out.println("Dominates somebody: " + mt.dominatesSomebody(point));
		List<Integer[]> dominated = mt.add(point);
		System.out.println("iDominated:");
		for (Integer[] dead : dominated) {
			System.out.println(Arrays.toString(dead));
		}
		System.out.println("done");
	}
	
	public static void test2(ArrayOfTrees aot, Integer[] point) {
		System.out.println("Adding point:" + Arrays.toString(point));
		aot.addPoint(point);
	}
}
