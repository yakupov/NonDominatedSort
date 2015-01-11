package org.itmo.iyakupov.nds.gen;

public class Point2DParallelFrontsGenerator implements ITestDataGen<int[][]> {
	final int nLevels = 10;
	
	@Override
	public int[][] generate(int dim, int max) {
		int step = max / nLevels;
		int levelSize = dim / nLevels;
		int[][] res = new int[levelSize * nLevels][2];
		for (int i = 0; i < nLevels; ++i) {
			for (int j = 0; j < levelSize; ++j) {
				res[i * levelSize + j][0] = (int)(Math.random() * max);
				res[i * levelSize + j][1] = i * step - res[i * levelSize + j][0];	
			}
		}
		return res;
	}
	
}
