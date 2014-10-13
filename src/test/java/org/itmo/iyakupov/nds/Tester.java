package org.itmo.iyakupov.nds;

import java.util.Arrays;
import java.util.List;

import org.itmo.iyakupov.nds.gen.ITestDataGen;
import org.itmo.iyakupov.nds.gen.Point2DTestDataGen;


public class Tester {

	public static void main(String[] args) {
		for (int i = 0; i < 10; ++i) {
			long start = System.nanoTime();
			testDebAndMy(false);
			long fin = System.nanoTime();
			double time = (double)(fin - start) / 1000.0;
			System.out.println("Deb works s: " + time);
			
			start = System.nanoTime();
			testDebAndMy(true);
			fin = System.nanoTime();
			time = (double)(fin - start) / 1000.0;
			System.out.println("iWork s: " + time);			
		}
		
		
		if (true) return;
		
		//MyTree mt = new MyTree();
		ArrayOfTrees aot = new ArrayOfTrees();
		
		Integer[][] testData = new Integer[][]{new Integer[]{4, 4}, new Integer[]{2, 6}, 
				new Integer[]{1, 8}, new Integer[]{2, 2}, new Integer[]{2, 1}, new Integer[]{5, 5}};
		
		for (Integer[] toAdd : testData) {
			test2(aot, toAdd);
			printRandomPoint(aot.getRandomPoint());
			//addAndPrintDominated(mt, toAdd);
		}
	}

	/**
	 * true == my
	 * @param mode
	 */
	public static void testDebAndMy(boolean mode) {
		ITestDataGen<Integer[][]> tdg = new Point2DTestDataGen();
		final int MAX = 10000;
		Integer[][] tests = tdg.generate(MAX, MAX);
		
		DebNSGA2 deb = new DebNSGA2();
		ArrayOfTrees my = new ArrayOfTrees();
		
		for (Integer[] currTest: tests) {
			if (mode)
				my.addPoint(currTest);
			else
				deb.addPoint(currTest);
		}
		
		if (!mode)
			deb.sort();
		
		for (int i = 0; i < tests.length; ++i) {
			if (mode)
				printRandomPoint(my.getRandomPoint());
			else
				printRandomPoint(deb.getRandomPoint());
		}
	}
	
	private static void printRandomPoint(Result r) {
		//System.out.printf("Rank: %d, point: %s\n", r.rank, Arrays.toString(r.point));
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
