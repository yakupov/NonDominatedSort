package org.itmo.iyakupov.nds.gen;

public interface ITestDataGen<E> {
	public E generate(int dim, int max);
}
