package org.itmo.iyakupov.nds.gen;

public class Point2DCircleFrontsGenerator implements ITestDataGen<Integer[][]> {
	final int nLevels = 10;
	
	@Override
	public Integer[][] generate(int dim, int max) {
		int step = max / nLevels;
		int levelSize = dim / nLevels;
		Integer[][] res = new Integer[levelSize * nLevels][2];
		for (int i = 0; i < nLevels; ++i) {
			int r = i * step;
			for (int j = 0; j < levelSize; ++j) {
				res[i * levelSize + j][0] = (int)(Math.random() * max);
				res[i * levelSize + j][1] = (int) Math.sqrt(Math.pow(res[i * levelSize + j][0], 2) - Math.pow(r, 2));	
			}
		}
		return res;
	}
	
}
