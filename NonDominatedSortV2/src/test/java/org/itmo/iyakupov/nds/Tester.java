package org.itmo.iyakupov.nds;

import java.math.BigDecimal;
import java.util.Arrays;

import org.itmo.iyakupov.nds.gen.ITestDataGen;
import org.itmo.iyakupov.nds.gen.Point2DCircleFrontsGenerator;
import org.itmo.iyakupov.nds.gen.Point2DDiagGenerator;
import org.itmo.iyakupov.nds.gen.Point2DParallelFrontsGenerator;
import org.itmo.iyakupov.nds.gen.Point2DUniSquareGen;
import org.itmo.iyakupov.nds.gen.Point2DUniStrireXPlusGen;


public class Tester {

	public static void main(String[] args) {
		//final int nRuns = 1;
		
		//test0();
		//test1();
		//test2();
		
		testRandSq();
		testRandPf();
		testRandCircle();
		testRandStripe();
	}
	
	
	private static void testRandPf() {
		testRandStupid(new Point2DParallelFrontsGenerator(), "parallel fronts");
	}
	
	private static void testRandCircle() {
		testRandStupid(new Point2DCircleFrontsGenerator(), "circle fronts");
	}
	
	private static void testRandSq() {
		testRandStupid(new Point2DUniSquareGen(), "square");
	}

	private static void testRandStupid(ITestDataGen<Integer[][]> gena, String name) {
		int dim = 10;
		try {
			dim = Integer.parseInt(System.getProperty("uniSquareTestDataDim"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Test data (" + name + ") dimension: " + dim);
		
		Integer[][] testData = gena.generate(dim, dim);
		for (int i = 0; i < testData.length; ++i) {
			System.out.print(Arrays.toString(testData[i]) + ' ');
		}
		System.out.println();
		
		testGeneric(testData);
	}
	
	private static void testRandStripe() {
		int dim = 10;
		try {
			dim = Integer.parseInt(System.getProperty("uniSquareTestDataDim"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Test data (stripe) dimension: " + dim);
		
		final int radius = 10;
		Integer[][] testData = new Point2DUniStrireXPlusGen(radius).generate(dim, dim);
		for (int i = 0; i < testData.length; ++i) {
			System.out.print(Arrays.toString(testData[i]) + ' ');
		}
		System.out.println();
		
		testGeneric(testData);
	}
		
	private static void testGeneric(Integer[][] testData) {
		System.gc();
		System.gc();
		
		long start = System.nanoTime();
		Population pop = new Population();
		for (Integer[] toAdd : testData) {
			pop.addPoint(new Int2DIndividual(toAdd));
		}
		long finish = System.nanoTime();
		System.out.println("iWork (s): " + new BigDecimal((finish-start) / 10e9).toPlainString());
		pop.validate();
		
		
		System.gc();
		System.gc();
		
		start = System.nanoTime();
		DebENLU popEnlu = new DebENLU();
		for (Integer[] toAdd : testData) {
			popEnlu.addPoint(new Int2DIndividual(toAdd));
		}
		finish = System.nanoTime();
		System.out.println("enluWorx (s): " + new BigDecimal((finish-start) / 10e9).toPlainString());
		popEnlu.validate();
		//System.err.println(popEnlu.toString());
	}
	
	
	public static void test0() {
		Population pop = new Population();
		Integer[][] testData = new Integer[][]{new Integer[]{4, 4}, new Integer[]{2, 6}, 
				new Integer[]{1, 8}, new Integer[]{2, 2}, new Integer[]{2, 1}, new Integer[]{5, 5}};
		
		/*
		 ^y 
		 *                    
		 *18
		 * 
		 *  26
		 *        55 
		 *    44
		 * 
		 *  22
		 *  21 
		 *------------------->x
		 */
		
		// 18 21; 22; 26 44; 55
		
		
		for (Integer[] toAdd : testData) {
			pop.addPoint(new Int2DIndividual(toAdd));
			System.out.println(pop.toString());
			//printRandomPoint(pop.getRandWithRank());
		}
		System.out.println(pop.toString());
	}
	
	public static void test1() {
		Population pop = new Population();
		Integer[][] testData = new Integer[][]{new Integer[]{4, 9}, new Integer[]{3, 2}, 
				new Integer[]{6, 7}, new Integer[]{2, 3}, new Integer[]{6, 2}, 
				new Integer[]{7, 3}, new Integer[]{2, 0}, new Integer[]{0, 7}, 
				new Integer[]{9, 1}, new Integer[]{1, 4}};
		
		// 07 14 20; 23 32 91; 49 62; 67 73;
		
		
		for (Integer[] toAdd : testData) {
			pop.addPoint(new Int2DIndividual(toAdd));
			System.out.println(pop.toString());
			//printRandomPoint(pop.getRandWithRank());
		}
		System.out.println(pop.toString());
	}
	

	/**
	 * Duplicate check
	 */
	public static void test2() {
		Population pop = new Population();
		Integer[][] testData = new Integer[][]{new Integer[]{0, 7}, 
				new Integer[]{7, 8}, new Integer[]{3, 1}, new Integer[]{6, 6}, 
				new Integer[]{7, 8}, new Integer[]{2, 5}, new Integer[]{2, 3}, 
				new Integer[]{6, 5}, new Integer[]{0, 3}, new Integer[]{3, 0}, };
		
		// 30 03; 07 23 31; 25; 65; 66; 78;
		
		
		for (Integer[] toAdd : testData) {
			pop.addPoint(new Int2DIndividual(toAdd));
			System.out.println(pop.toString());
			//printRandomPoint(pop.getRandWithRank());
		}
		System.out.println(pop.toString());
	}
	

	public static void execTest1(final int nRuns) {
		for (int i = 0; i < nRuns; ++i) {
			long start = System.nanoTime();
			testDebAndMy(false, new Point2DDiagGenerator());
			long fin = System.nanoTime();
			double time = (double)(fin - start) / 1000.0;
			System.out.println("Deb works s: " + time);
			
			start = System.nanoTime();
			testDebAndMy(true, new Point2DDiagGenerator());
			fin = System.nanoTime();
			time = (double)(fin - start) / 1000.0;
			System.out.println("iWork s: " + time);			
		}
	}

	/**
	 * true == my
	 * @param mode
	 */
	public static void testDebAndMy(boolean mode, ITestDataGen<Integer[][]> tdg) {
		System.gc();
		System.gc();
		final int MAX = 10000;
		Integer[][] tests = tdg.generate(MAX, MAX);
		
		DebNSGA2 deb = new DebNSGA2();
		Population my = new Population();
		
		for (Integer[] currTest: tests) {
			if (mode)
				my.addPoint(new Int2DIndividual(currTest));
			else
				deb.addPoint(currTest);
		}
		
		if (!mode)
			deb.sort();
		
		for (int i = 0; i < tests.length; ++i) {
			if (mode)
				printRandomPoint(my.getRandWithRank());
			else
				printRandomPoint(deb.getRandomPoint());
		}
	}

	private static void printRandomPoint(Object randWithRank) {
		System.err.println(randWithRank.toString());		
	}
}
