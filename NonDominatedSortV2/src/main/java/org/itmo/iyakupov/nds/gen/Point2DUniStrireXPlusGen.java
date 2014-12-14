package org.itmo.iyakupov.nds.gen;

public class Point2DUniStrireXPlusGen implements ITestDataGen<Integer[][]> {
	protected int radius;
	public Point2DUniStrireXPlusGen(int radius) {
		this.radius = radius;
	}
	
	@Override
	public Integer[][] generate(int dim, int max) {
		Integer[][] res = new Integer[dim][2];
		for (int i = 0; i < dim; ++i) {
			res[i][0] = (int)(Math.random() * max);
			res[i][1] = (int)(Math.random() * max);
			while (res[i][1] < res[i][0] - radius || res[i][1] > res[i][0] + radius) {
				res[i][1] = (int)(Math.random() * max);
			}
		}
		return res;
	}

}
