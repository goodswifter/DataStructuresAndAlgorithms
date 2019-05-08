package com.ad.arraylist;

import com.ad.AbstractList;

@SuppressWarnings("unchecked")
public class ArrayList<E> extends AbstractList<E> {
	
	/**
	 * 所有的元素
	 */
	private E[] elements;
	// 默认容量
	private static final int DEFAULT_CAPACITY = 10;

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayList(int capacity) {
		capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
		elements = (E[]) new Object[capacity];
	}

	/**
	 * 清除所有元素
	 */
	public void clear() {
		// 将存储对象的地址置空, 防止浪费资源
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * 获取index位置的元素
	 * @param index
	 * @return 当前索引的元素
	 */
	public E get(int index) { // O(1)
		rangeCheck(index);
		
		return elements[index];
	}

	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	public E set(int index, E element) { // O(1)
		rangeCheck(index);
		
		E oldElement = elements[index];
		elements[index] = element;
		
		return oldElement;
	}

	/**
	 * 在index位置插入一个元素
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) {
		/**
		 * 最好: O(1)
		 * 最坏: O(n)
		 * 平均: O(1) + O(2) + ... + O(n) = O((1 + n) * n / 2 / n) = O(n)
		 */
		rangeCheckForAdd(index);
		
		ensureCapacity(size + 1);
		
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1]; 
		}
		
		elements[index] = element;
		size++;
	}

	/**
	 * 删除index位置的元素
	 * @param index
	 * @return 删除位置的值
	 */
	public E remove(int index) {
		/**
		 * 最好: O(1)
		 * 最坏: O(n)
		 * 平均: O(n)
		 */
		rangeCheck(index);
		
		E oldElement = elements[index];
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}
		
		elements[--size] = null;
		
		return oldElement;
	}

	/**
	 * 查看元素的索引
	 * @param element
	 * @return 元素的索引, 未找到返回-1
	 */
	public int indexOf(E element) {
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (elements[i] == null) return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (element.equals(elements[i])) return i;
			}
		}
		
		return ELEMENT_NOT_FOUND;
	}

	/**
	 * 保证有capacity的容量, 扩容1.5倍
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("size = ").append(size).append(", [");
		
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(elements[i]);
		}
		
		builder.append("]");
		
		return builder.toString();
	}
}
