package org.itmo.iyakupov.nds;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.itmo.iyakupov.nds.gen.ITestDataGen;
import org.itmo.iyakupov.nds.gen.Point2DCircleFrontsGenerator;
import org.itmo.iyakupov.nds.gen.Point2DDiagGenerator;
import org.itmo.iyakupov.nds.gen.Point2DParallelFrontsGenerator;
import org.itmo.iyakupov.nds.gen.Point2DUniSquareGen;
import org.itmo.iyakupov.nds.gen.Point2DUniStrireXPlusGen;

import au.com.bytecode.opencsv.CSVWriter;

public class Tester {
	public static void main(String[] args) {
		List<String[]> results = new ArrayList<String[]>();

		for (int i = 0; i < 10; ++i) {
			testRandSq(results);
			testRandPf(results);
			testRandCircle(results);
			testRandStripe(results);
			testRandDiag(results);
		}
		
		try {
			saveToCsvFile("lastRun.csv", results);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static void testRandDiag(List<String[]> results) {
		testRandStupid(new Point2DDiagGenerator(), "diag", results);
	}

	private static void testRandPf(List<String[]> results) {
		testRandStupid(new Point2DParallelFrontsGenerator(), "parallel fronts", results);
	}

	private static void testRandCircle(List<String[]> results) {
		testRandStupid(new Point2DCircleFrontsGenerator(), "circle fronts", results);
	}

	private static void testRandSq(List<String[]> results) {
		testRandStupid(new Point2DUniSquareGen(), "square", results);
	}

	private static void testRandStupid(ITestDataGen<Integer[][]> gena, String name, List<String[]> results) {
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

		testGeneric(testData, name, results);
	}

	private static void testRandStripe(List<String[]> results) {
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

		testGeneric(testData, "stripe", results);
	}

	public static void saveToCsvFile(String fileName, List<String[]> data) throws IOException {
		FileWriter fw = new FileWriter(fileName, false); //not append
 		Writer writer = new BufferedWriter(fw);
		CSVWriter csvWriter = new CSVWriter(writer, ';');
		csvWriter.writeAll(data);
		csvWriter.close();
		writer.close();
		fw.close();
	}

	private static void testGeneric(Integer[][] testData, String testName, List<String[]> results) {
		boolean validate = "Y".equals(System.getProperty("uniSquareTestDataDim"));

		System.gc();
		System.gc();
		Population pop = new Population();
		long start = System.nanoTime();
		for (Integer[] toAdd : testData) {
			pop.addPoint(new Int2DIndividual(toAdd));
		}
		long finish = System.nanoTime();
		long comparsions = Int2DIndividual.dominationComparsionCount;
		Int2DIndividual.dominationComparsionCount = 0;
		printResults(results, "my", start, finish, comparsions, testName);
		if (validate)
			pop.validate();

		System.gc();
		System.gc();
		DebENLU popEnlu = new DebENLU();
		start = System.nanoTime();
		for (Integer[] toAdd : testData) {
			popEnlu.addPoint(new Int2DIndividual(toAdd));
		}
		finish = System.nanoTime();
		comparsions = Int2DIndividual.dominationComparsionCount;
		Int2DIndividual.dominationComparsionCount = 0;
		printResults(results, "ENLU", start, finish, comparsions, testName);
		if (validate)
			popEnlu.validate();

		System.gc();
		System.gc();
		DebNSGA2 deb = new DebNSGA2();
		start = System.nanoTime();
		for (Integer[] currTest: testData) {
			deb.addPoint(currTest);
		}
		comparsions = deb.sort();
		finish = System.nanoTime();
		printResults(results, "NSGA2", start, finish, comparsions, testName);
	}

	private static void printResults(List<String[]> results, String algo, long start, long finish, long comparsions, String testName) {
		String runningTimeSecs = new BigDecimal((finish-start) / 10e9).toPlainString();
		System.out.println(algo + " running time (s): " + runningTimeSecs);
		System.out.println(algo + " comparsions count: " + comparsions);
		
		if (results.isEmpty()) {
			results.add(new String[] {"test name", "algo", "running time (s)", "comparsions count"});
		}
		results.add(new String[] {testName, algo, runningTimeSecs, String.valueOf(comparsions)});
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
		}
		System.out.println(pop.toString());
	}
}
