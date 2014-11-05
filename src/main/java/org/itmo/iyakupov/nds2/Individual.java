package org.itmo.iyakupov.nds2;

public class Individual {
	protected Integer[] value;
	protected int rg;
	
	public Individual(Integer[] value, int rg) {
		super();
		this.value = value;
		this.rg = rg;
	}

	public int getRg() {
		return rg;
	}

	public void setRg(int rg) {
		this.rg = rg;
	}

	public Integer[] getValue() {
		return value;
	}
}
