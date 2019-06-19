package com.ad.tree;

import java.util.Comparator;

public class AVLTree<E> extends BST<E> {
	public AVLTree() {
		this(null);
	}
	
	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) { // 是否平衡
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				rebalance(node);
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) { // 是否平衡
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				rebalance(node);
			}
		}
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<E>(element, parent);
	}
	
	/**
	 * 恢复平衡
	 * @param grand 高度最低的那个补平衡点
	 */
	private void rebalance(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) { // L 左子树高
			if (node.isLeftChild()) { // LL 
				rotateRight(grand);
			} else { // LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		} else { // R
			if (node.isLeftChild()) { // RL 
				rotateRight(parent);
				rotateLeft(grand);
			} else { // RR
				rotateLeft(grand);
			}
		}
	}
	
	private void rotateLeft(Node<E> grand) {
		// 需要调整的父节点
		Node<E> parent = grand.right;
		// 需要调整的子节点
		Node<E> child = parent.left;
		grand.right = parent.left;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	private void rotateRight(Node<E> grand) {
		// 需要调整的父节点
		Node<E> parent = grand.left;
		// 需要调整的子节点
		Node<E> child = parent.right;
		grand.left = parent.right;
		parent.right = grand;
		
		afterRotate(grand, parent, child);
	}
	
	
	private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		// 更改parent的父节点
		parent.parent = grand.parent;
		// 更改 grand 父节点的子树指向
		if (grand.isLeftChild()) { // grand 是左节点
			grand.parent.left = parent; 
		} else if (grand.isRightChild()) { // grand 是右节点
			grand.parent.right = parent;
		} else { // grand 是根节点
			root = parent;
		}
		
		// 更改 child 的父类
		if (child != null) {
			child.parent = grand;
		}
		
		// 更改 grand 的父类
		grand.parent = parent;
		
		// 更新高度
		updateHeight(grand);
		updateHeight(parent);
	}
	
	/**
	 * 更新节点的高度
	 * @param node
	 */
	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updateHeight();
	}
	
	/**
	 * 判断以这个节点为根节点的二叉树是否平衡
	 * @param node
	 * @return
	 */
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
	
	private static class AVLNode<E> extends Node<E> {
		int height = 1;
		
//		int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
//		int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		public int balanceFactor() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
		
		public void updateHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) return left;
			if (leftHeight < rightHeight) return right;
			return isLeftChild() ? left : right;
		}
		
		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}
}
