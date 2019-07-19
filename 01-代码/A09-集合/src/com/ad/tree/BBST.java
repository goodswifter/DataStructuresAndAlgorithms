package com.ad.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.ad.printer.BinaryTreeInfo;
import com.ad.tree.BinaryTree.Node;

@SuppressWarnings({"unused"})
public class BBST<E> extends BST<E> {

	private Comparator<E> comparator;
	
	public BBST() {
		this(null);
	}

	public BBST(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	protected void rotateLeft(Node<E> grand) {
		// 需要调整的父节点
		Node<E> parent = grand.right;
		// 需要调整的子节点
		Node<E> child = parent.left;
		grand.right = parent.left;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void rotateRight(Node<E> grand) {
		// 需要调整的父节点
		Node<E> parent = grand.left;
		// 需要调整的子节点
		Node<E> child = parent.right;
		grand.left = parent.right;
		parent.right = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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
	}
}
