package com.ad;

@SuppressWarnings("unchecked")
public class ArrayList<E> {
	/**
	 * 元素的数量
	 */
	private int size;
	
	/**
	 * 所有的元素
	 */
	private E[] elements;

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayList(int capacity) {
		capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
		elements = (E[]) new Object[capacity];
	}
	
	// 默认容量
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;

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
	 * 元素的数量
	 * @return
	 */
	public int size() {
		return size;
	}
	
	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 是否包含某个元素
	 * @param element
	 * @return
	 */
	public boolean contains(E element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
	}

	/**
	 * 添加元素到尾部
	 * @param element
	 */
	public void add(E element) {
		this.add(size, element);
	}

	/**
	 * 获取index位置的元素
	 * @param index
	 * @return 当前索引的元素
	 */
	public E get(int index) {
		rangeCheck(index);
		
		return elements[index];
	}

	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	public E set(int index, E element) {
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
		rangeCheckForAdd(index);
		
		ensureCapacity(size + 1);
		
		for (int i = size - 1; i >= index; i--) {
			elements[i + 1] = elements[i]; 
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
		rangeCheck(index);
		
		E oldELement = elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[--size] = null;
		
		return oldELement;
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
	
	private void outOfBounds(int index) {
		throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
	}
	
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			outOfBounds(index);
		}
	}
	
	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			outOfBounds(index);
		}
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
