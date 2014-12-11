package org.itmo.iyakupov.nds;

import java.util.Arrays;

public class Result {
	public int rank;
	public Integer[] point;
	public Result(int rank, Integer[] point) {
		super();
		this.rank = rank;
		this.point = point;
	}
	
	public String toString() {
		return "Rank = " + rank + ", value = " + Arrays.toString(point);
	}
}