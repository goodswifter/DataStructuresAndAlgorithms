package com.ad.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.ad.printer.BinaryTreeInfo;

@SuppressWarnings({"unchecked", "unused"})
public class BST<E> extends BinaryTree<E> {

	private Comparator<E> comparator;
	
	public BST() {
		this(null);
	}

	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		
		// 添加第一个节点
		if (root == null) {
			root = createNode(element, null);
			size++;
			
			// 添加新节点之后的处理
			afterAdd(root);
			
			return;
		}
		
		// 添加的不是第一个节点
		// 找到父节点
		Node<E> parent = root;
		Node<E> node = root;
		int cmp = 0;
		while (node != null) {
			cmp = compare(element, node.element);
			
			parent = node;
			
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { // 自定义对象时, 需要覆盖
				node.element = element;
				return;
			}
		}
		
		Node<E> newNode = createNode(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else if (cmp < 0) {
			parent.left = newNode;
		}
		
		size++;
		
		// 添加新节点之后的处理
		afterAdd(newNode);
	}
	
	/**
	 * 添加node之后的调整
	 * 
	 * @param node 新添加的节点
	 */
	protected void afterAdd(Node<E> node) { }
	
	/**
	 * 删除node之后的调整
	 * 
	 * @param node 被删除的节点
	 */
	protected void afterRemove(Node<E> node) { }
	
	public void remove(E element) {
		remove(node(element));
	}
	
	/**
	 * 根据元素内容获取节点
	 */
	private Node<E> node(E element) {
		elementNotNullCheck(element);
		
		Node<E> node = root;
		while (node != null) {
			int cmp = compare(element, node.element);
			if (cmp == 0) return node;
			if (cmp > 0) {
				node = node.right;
			} else {
				node = node.left;
			}
		}
		return node;
	}
	
	/**
	 * 分析:
	 * 删除节点有三种情况:
	 * 
	 * 1. 删除 `度为2` 的节点
	 * 		先用前驱或者后继节点的值覆盖原节点的值
	 * 		然后删除相应的前驱或者后继节点
	 * 		它的前驱或者后继节点的度只可能是 1 或者 0
	 * 
	 * 2. 删除 `度为1` 的节点
	 * 		用子节点代替原节点的位置, 也就是用 child 替代 node 的位置
	 * 		2.1 如果 child 是 node 的左子节点
	 * 			child.parent = node.parent
	 * 			node.parent.left = child
	 * 
	 * 		2.2 如果 child 是 node 的右子节点
	 * 			child.parent = node.parent
	 * 			node.parent.right = child
	 * 
	 * 		2.3 如果 node 是根节点
	 * 			root = child
	 * 			child.parent = nul1
	 * 		
	 * 
	 * 3. 删除叶子节点
	 * 		直接删除
	 * 		3.1 左节点
	 * 		node == node.parent.left
	 * 		node.parent.left = null
	 * 		
	 * 		3.2 右节点
	 * 		node == node.parent.right
	 * 		node.parent.right = null
	 * 		
	 * 		3.3 根节点
	 * 		node.parent = null
	 * 		root = null
	 * 
	 */
	private void remove(Node<E> node) {
		if (node == null) return;
	
		size--;
		
		// 1. 删除度为2的节点
		if (node.isHasTwoChild()) {
			// 前驱节点
			Node<E> pre = predecessor(node);
			// 用前驱节点的值覆盖要删除节点的值
			node.element = pre.element;
			// 删除前驱节点
			node = pre;
		}
		
		Node<E> replacement = node.left != null ? node.left : node.right;

		// 2. 删除度为1的节点
		if (replacement != null) {
			// 更改 parent
			replacement.parent = node.parent;
			// 更改 parent 的 left、right 的指向
			if (node.parent == null) { // node 是度为1的节点并且是根节点
				root = replacement;
				// replacement.parent = null; // 因为在更改parent的时候已经设置了
			} else if (node == node.parent.left) { // 左节点
				node.parent.left = replacement;
			} else { // 右节点 : node == node.parent.right
				node.parent.right = replacement;
			}
		} else if (node.parent == null) { // node是叶子节点并且是根节点
			root = null;
			node = root;
		} else { // node是叶子节点, 但不是根节点
			if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { // node == node.parent.right
				node.parent.right = replacement;
			}
		}
		
		// 删除之后的处理
		afterAdd(node);
	}
	
	/**
	 * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
	 */
	private int compare(E e1, E e2) {
		// 既可以给我传个比较器, 又可以不传
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}	
		return ((Comparable<E>)e1).compareTo(e2);
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
