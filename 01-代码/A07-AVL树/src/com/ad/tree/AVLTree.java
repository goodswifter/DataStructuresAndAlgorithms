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
	
	private void rotateLeft(Node<E> node) {
		
	}
	
	private void rotateRight(Node<E> node) {
		
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
		
		int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
		int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		public int balanceFactor() {
//			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
//			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
		
		public void updateHeight() {
//			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
//			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		
		public Node<E> tallerChild() {
			if (leftHeight > rightHeight) return left;
			if (leftHeight < rightHeight) return right;
			return isLeftChild() ? left : right;
		}
	}
}
