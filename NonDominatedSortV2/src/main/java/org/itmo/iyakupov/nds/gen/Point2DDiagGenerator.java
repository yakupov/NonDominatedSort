package org.itmo.iyakupov.nds.gen;

public class Point2DDiagGenerator implements ITestDataGen<Integer[][]> {

	@Override
	public Integer[][] generate(int dim, int max) {
		Integer[][] res = new Integer[max][2];
		for (int i = max; i > 0; --i) {
			res[max - i][0] = i;
			res[max - i][1] = i;
		}
		return res;
	}

}
