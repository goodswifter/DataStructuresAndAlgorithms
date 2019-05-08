package com.ad.circle;

@SuppressWarnings("unchecked")
public class CircleDeque<E> {
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public CircleDeque() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 从头部入队
	 * @param element
	 */
	public void enQueueFront(E element) {
		ensureCapacity(size + 1);
		
		front = index(-1);
		elements[front] = element;
		size++;
	}

	/**
	 * 从尾部入队
	 * @param element
	 */
	public void enQueueRear(E element) {
		ensureCapacity(size + 1);
		
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 从头部出队
	 * @param element
	 */
	public E deQueueFront() {
		E frontElement = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElement;
	}

	/**
	 * 从尾部出队
	 * @param element
	 */
	public E deQueueRear() {
		E rearElement = elements[index(size - 1)];
		elements[index(size - 1)] = null;
		size--;
		return rearElement;
	}

	public E front() {
		return elements[front];
	}

	public E rear() {
		return elements[index(size - 1)];
	}
	
	/**
	 * 找到真实的索引位置
	 * @param index
	 * @return
	 */
	private int index(int index) {
		int length = elements.length;
		index += front;
		if (index < 0) {
			return index + length;
		}
		// 尽量避免使用乘*、除/、模%、浮点数运算, 效率低下
//		return index % length;
		/**
		 * 已知n>=0, m>0
		 * n % m 等价于 n - (m > n ? 0 : m) 的前提条件: n < 2m
		 */
		return index - (length > index ? 0 : length);
	}
	
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量扩容到旧容量的 1.5 倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;
		
		// 重置front
		front = 0;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("capcacity=").append(elements.length)
		.append(" size=").append(size)
		.append(" front=").append(front)
		.append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}
			
			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}
}
