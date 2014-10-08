package org.itmo.iyakupov.nds.util;

import java.util.ArrayList;
import java.util.List;

public class MyStack<E> extends ArrayList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyStack() {
		super();
	}
	
	public MyStack (int i) {
		super(i);
	}
	
	public MyStack (List<E> orig) {
		super(orig);
	}
	
	public E pop() {
		if (isEmpty())
			return null;
		E removed = get(size() - 1);
		this.remove(size() - 1);
		return removed;
	}
	
	public void push(E e) {
		add(e);
	}
}
