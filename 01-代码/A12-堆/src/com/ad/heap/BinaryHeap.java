package com.ad.heap;

import java.util.Comparator;

import com.ad.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements Heap<E>, BinaryTreeInfo {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	
	
	public BinaryHeap(E[] elements, Comparator<E> comparator) {
		super(comparator);
		
		if (elements == null || elements.length == 0) {
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		} else {
			size = elements.length;
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
			
			// 二叉堆化/批量建堆
			heapify();
		}
	}

	public BinaryHeap() {
		this(null, null);
	}
	
	public BinaryHeap(Comparator<E> comparator) {
		this(null, comparator);
	}
	
	public BinaryHeap(E[] elements) {
		this(elements, null);
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
		emptyCheck();
		
		E root = elements[0];
		int lastIndex = --size;
		elements[0] = elements[lastIndex];
		elements[lastIndex] = null;
	
		// 下滤
		siftDown(0);
		
		return root;
	}

	@Override
	public E replace(E element) {
		elementNotNullCheck(element);
		
		E root = null;
		if (size == 0) {
			elements[0] = element;
			size++;
		} else {
			root = elements[0];
			elements[0] = element;
			siftDown(0);
		}
		
		return root;
	}


	/**
	 * 二叉堆化/批量建堆
	 */
	private void heapify() {
		// 自上而下的上滤
//		for (int i = 1; i < size; i++) {
//			siftUp(i);
//		}
		
		// 自下而上的下滤
		// 从非叶子节点开始,自下而上下滤
		for (int i = (size >> 1) - 1; i >= 0; i--) {
			siftDown(i);
		}
	}

	/**
	 * 让index位置的元素下滤
	 * @param index
	 */
	private void siftDown(int index) {
		E element = elements[index];
		int half = size >> 1;
		// 第一个叶子节点的索引 == 非叶子节点的数量
		// index < 第一个叶子节点的索引
		// 必须保证index位置是非叶子节点
		while (index < half) {
			// index的节点有2种情况
			// 1. 只有左子节点
			// 2. 同时有左右子节点
			
			// 默认跟左子节点跟它比较
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];
			
			// 右子节点索引
			int rightIndex = childIndex + 1;
			
			// 选出左右子节点最大的那个
			if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
				child = elements[childIndex = rightIndex];
			}
			
			if (compare(element, child) >= 0) break;
			
			// 将子节点存放到index位置
			elements[index] = child;
			
			// 重新设置index
			index = childIndex;
		}
		
		elements[index] = element;
	}

	/**
	 * 让index位置的元素上滤
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
