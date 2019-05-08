package com.ad.circlelinkedlist;

import com.ad.AbstractList;

public class CircleLinkedList<E> extends AbstractList<E> {
	
	private Node<E> first;
	private Node<E>	last;
	
	private static class Node<E> {
		Node<E> prev;
		E element;
		Node<E> next;
		
		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.element = element;
			this.next = next;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(prev.element).append("_");
			sb.append(element).append("_");
			sb.append(next.element);
			return sb.toString();
		}
	}

	@Override
	public E get(int index) {
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E oldE = node.element;
		node.element = element;
		return oldE;
	}

	@Override
	// 注意: 如果不是gc root对象, 其他的对象都会被释放
	// >1 栈空间的变量也就是局部变量引用的对象就是gc root 对象
	public void clear() {
		size = 0;
		first = null;
		last = null;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		
		if (index == size) {
			Node<E> oldLast = last;
			last = new Node<E>(oldLast, element, first);
			
			if (size == 0) { // 空链表
				first = last;
				first.next = first;
				last.prev = first;
			} else {
				oldLast.next = last;
				first.prev = last;
			}
		} else {
			Node<E> prev = node(index).prev;

			Node<E> next = node(index);
			Node<E> newNode = new Node<E>(prev, element, next);
			
			next.prev = newNode;
			prev.next = newNode;
			
			// 在第0个位置添加元素时, 修改当前的first
			if (next == first) { // index == 0
				first = newNode;
			}
		}
		
		size++;
	}

	@Override
	public E remove(int index) {
		rangeCheck(index);
		
		Node<E> deleteNode = node(index);
		if (size == 1) {
			first = null;
			last = null;
		} else {
			Node<E> prev = deleteNode.prev;
			Node<E> next = deleteNode.next;
			prev.next = next;
			next.prev = prev;
			
			if (index == 0) {
				first = next;
			}
			
			if (index == size - 1) {
				last = prev;
			}
		}
		
		size--;
		
		return deleteNode.element;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null) return i;
				
				node = node.next;
			}
		} else {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element)) return i;
				
				node = node.next;
			}
		}
		
		return ELEMENT_NOT_FOUND;
	}
	
	/**
	 * 获取index位置对应的节点对象
	 * @param index
	 * @return
	 */
	private Node<E> node(int index) {
		rangeCheck(index);
		
		if (index < (size >> 1)) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("size = ").append(size).append(", [");
		
		Node<E> node = first;
		
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(node);
			
			node = node.next; 
		}
		
		builder.append("]");
		
		return builder.toString();
	}
}
