package com.ad.set;

public interface Set<E> {
	int size();
	boolean isEmpty();
	void clear();
	boolean contains(E element);
	void add(E element);
	void remove(E element);
	void traversal(Visitor<E> visitor);
	
	public static abstract class Visitor<E> {
		boolean stop;
		/*
		 * 返回值代表是否停止遍历
		 */
		public abstract boolean visit(E element);
	}
}
