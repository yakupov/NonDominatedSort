package org.itmo.iyakupov.nds;

import java.util.Arrays;
import java.util.List;

public class Tester {

	public static void main(String[] args) {
		MyTree mt = new MyTree();

		Integer[][] testData = new Integer[][]{new Integer[]{4, 4}, new Integer[]{2, 6}, 
				new Integer[]{1, 8}, new Integer[]{4, 2}, new Integer[]{4, 1}};
		
		for (Integer[] toAdd : testData) {
			addAndPrintDominated(mt, toAdd);
		}
	}

	public static void addAndPrintDominated(MyTree mt, Integer[] point) {
		System.out.println("Adding point:" + Arrays.toString(point));
		List<Integer[]> dominated = mt.add(point);
		System.out.println("iDominated:");
		for (Integer[] dead : dominated) {
			System.out.println(Arrays.toString(dead));
		}
		System.out.println("done");
	}
}
