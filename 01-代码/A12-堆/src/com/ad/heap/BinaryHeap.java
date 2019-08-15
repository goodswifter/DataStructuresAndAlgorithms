package com.ad.heap;

import java.util.Comparator;

import com.ad.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements Heap<E>, BinaryTreeInfo {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public BinaryHeap() {
		this(null);
	}
	
	public BinaryHeap(Comparator<E> comparator) {
		super();
		this.elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public void add(E element) {
		// 检查元素是否为空
		elementNotNullCheck(element);
		
		// 是否需要扩容
		ensureCapacity(size + 1);
		
		elements[size++] = element;
		
		// 上滤
		siftUp(size - 1);
	}

	@Override
	/**
	 * 获取最大值
	 */
	public E get() {
		emptyCheck();
		
		return elements[0];
	}

	@Override
	public E remove() {
		return null;
	}

	@Override
	public E replace(E element) {
		return null;
	}

	/**
	 * 上滤
	 * @param index
	 */
	private void siftUp(int index) {
		E element = elements[index];
		while (index > 0) {
			// 父索引
			int parentIndex = (index - 1) >> 1;
			E parent = elements[parentIndex];
			if (compare(element, parent) <= 0) break;
			
			// 将父元素存储在index位置
			elements[index] = parent;
			
			// 重新赋值index
			index = parentIndex;
		}
		elements[index] = element;
	}

	/**
	 * 保证有capacity的容量
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量是旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newELements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newELements[i] = elements[i];
		}
		
		elements = newELements;
		
		System.out.println(oldCapacity + "扩容为" + newCapacity);
	}
	
	/**
	 * 检查堆是否为空
	 */
	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Heap is empty");
		}
	}
	
	/**
	 * 添加的元素不能为空 
	 */
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

	@Override
	public Object root() {
		return 0;
	}

	@Override
	public Object left(Object node) {
		Integer index = (Integer) node;
		index = (index << 1) + 1;
		return index >= size ? null : index;
	}

	@Override
	public Object right(Object node) {
		Integer index = (Integer) node;
		index = (index << 1) + 2;
		return index >= size ? null : index;
	}

	@Override
	public Object string(Object node) {
		Integer index = (Integer) node;
		return elements[index];
	}

}
