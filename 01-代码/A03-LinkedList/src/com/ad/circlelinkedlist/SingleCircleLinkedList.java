package com.ad.circlelinkedlist;

import com.ad.AbstractList;

public class SingleCircleLinkedList<E> extends AbstractList<E> {
	
	private Node<E> first;
	
	private static class Node<E> {
		E element;
		Node<E> next;
		
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(element).append("_").append(next.element);
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
	public void clear() {
		size = 0;
		first = null;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		
		if (index == 0) {
			Node<E> newFirst = new Node<>(element, first);
			Node<E> last = size == 0 ? newFirst : node(size - 1);
			first = newFirst;
			last.next = first;
		} else {
			Node<E> prev = node(index - 1);
			prev.next = new Node<>(element, prev.next);
		}
		
		size++;
	}

	@Override
	public E remove(int index) {
		rangeCheck(index);
		
		Node<E> node = first;
		if (size == 1) {
			clear();
		} else {
			if (index == 0) {
				Node<E> last = node(size - 1);
				// 这句话必须放在上一句的后面, 不然last节点就会找错
				first = first.next;
				last.next = first;
			} else {
				Node<E> prev = node(index - 1);
				node = prev.next;
				prev.next = node.next;
			}
			
			size--;
		}
		
		return node.element;
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
		
		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
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
