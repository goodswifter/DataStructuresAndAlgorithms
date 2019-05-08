package com.ad.circle;

@SuppressWarnings("unchecked")
public class CircleQueue<E> {
	private int front;
	private int size;
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public CircleQueue() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public void enQueue(E element) {
		ensureCapacity(size + 1);
		
		elements[index(size)] = element;
		size++;
	}

	public E deQueue() {
		E frontElement = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElement;
	}

	public E front() {
		return elements[front];
	}
	
	/**
	 * 找到真实的索引位置
	 * @param index
	 * @return
	 */
	private int index(int index) {
		int length = elements.length;
		
//		return (front + index) % length;
		return index - (length > index ? 0 : length);
	}
	
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量扩容到旧容量的 1.5 倍
		int newCapacity = oldCapacity + oldCapacity >> 1;
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
