package com.ad.heap;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public abstract class AbstractHeap<E> implements Heap<E> {
	protected int size;
	private Comparator<E> comparator;

	public AbstractHeap() {
		this(null);
	}
	
	public AbstractHeap(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 比较两个元素的大小
	 * @param e1
	 * @param e2
	 * @return
	 */
	protected int compare(E e1, E e2) {
		return comparator != null ? comparator.compare(e1, e2) : ((Comparable<E>)e1).compareTo(e2);
	}

}
