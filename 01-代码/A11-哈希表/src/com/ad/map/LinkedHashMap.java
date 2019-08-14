package com.ad.map;

import java.util.Objects;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LinkedHashMap<K, V> extends HashMap<K, V> {
	private LinkedNode<K, V> first;
	private LinkedNode<K, V> last;
	
	/**
	 * 清空HashMap
	 */
	@Override
	public void clear() {
		super.clear();
		first = null;
		last = null;
	}
	
	@Override
	public boolean containsValue(V value) {
		LinkedNode<K, V> node = first;
		while (node != null) {
			if (Objects.equals(value, node.value)) return true;
			node = node.next;
		}
		return false;
	};
	
	@Override
	public void traversal(Map.Visitor<K,V> visitor) {
		if (visitor == null) return;
		LinkedNode<K, V> node = first;
		while (node != null) {
			if (visitor.visit(node.key, node.value)) return;
			node = node.next;
		}
	};
	
	protected void resize() {
		// 装填因子 <= 0.75
		if (size / table.length <= DEFAULT_LOAD_FACTOR) return;
		
		Node<K, V>[] oldTable = table;
		table = new Node[oldTable.length << 1];
		
		LinkedNode<K, V> node = first;
		while (node != null) {
			moveNode(node);
			node = node.next;
		}
	};
	
	@Override
	protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
		LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
		LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;
		
		if (node1 != node2) { // 删除度为2的节点
			// 1. 交换两个节点
			exchangeNode(node1, node2);
		}

		LinkedNode<K, V> prev = node2.prev;
		LinkedNode<K, V> next = node2.next;
		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
		}
		
		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
		}
	}
	
	/**
	 * 交换双向链表的两个节点
	 * @param node1
	 * @param node2
	 */
	private void exchangeNode(LinkedNode<K, V> node1, LinkedNode<K, V> node2) {
		// 1. 交换prev
		LinkedNode<K, V> tmpPrev = node1.prev;
		node1.prev = node2.prev;
		node2.prev = tmpPrev;
		if (node1.prev == null) {
			 first = node1;
		} else {
			node1.prev.next = node1;
		}
		if (node2.prev == null) {
			 first = node2;
		} else {
			node2.prev.next = node2;
		}
		
		// 2. 交换next
		LinkedNode<K, V> tmpNext = node1.next;
		node1.next = node2.next;
		node2.next = tmpNext;
		if (node1.next == null) {
			 last = node1;
		} else {
			node1.next.prev = node1;
		}
		if (node2.next == null) {
			 last = node2;
		} else {
			node2.next.prev = node2;
		}
	}

	@Override
	protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
		LinkedNode<K, V> node = new LinkedNode(key, value, parent);
		if (first == null) {
			first = last = node; 
		} else {
			last.next = node;
			node.prev = last;
			last = node;
 		}
		return node;
	}
	
	private static class LinkedNode<K, V> extends Node<K, V> {
		private LinkedNode<K, V> prev;
		private LinkedNode<K, V> next;

		public LinkedNode(K key, V value, Node<K, V> parent) {
			super(key, value, parent);
		}
		
	}
	
	
}
