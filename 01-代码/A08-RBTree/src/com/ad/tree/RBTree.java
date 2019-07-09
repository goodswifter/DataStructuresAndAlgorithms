package com.ad.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	public RBTree() {
		this(null);
	}
	
	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	/**
	 * 添加
	 */
	protected void afterAdd(Node<E> node) {
		// 父节点
		Node<E> parent = node.parent;
		
		// 1. 如果是根节点
		if (node.parent == null) {
			black(node);
			return;
		}
		
		// 2. 如果父节点是黑色, 直接返回(因为默认节点是红色)
		if (isBlack(parent)) return;
		
		// 叔父节点
		Node<E> uncle = parent.sibling();
		// 祖父节点
		Node<E> grand = red(parent.parent);
		
		// 剩下8种情况不符合红黑树性质4
		// 3. uncle 是 红色, 有四种情况, 如果产生上溢, 继续处理
		// 3.1 parent 和 uncle 染成 黑色
		// 3.2 grand 向上合并, 染成 红色, 当做新添加的节点进行处理
		// 3.3. 如果产生上溢, 继续处理
		if (isRed(uncle)) {
			black(parent);
			black(uncle);
			// 把祖父节点当做新添加的节点
//			afterAdd(red(grand));
			afterAdd(grand);
			return;
		}
		
		// 4. uncle 不是 RED, 有四种情况
		// 4.1 parent 染成 黑色, grand 染成 红色
		// 4.2 对 grand 进行单旋转, LL : 右旋转, RR : 左旋转
		if (parent.isLeftChild()) { // L
//			red(grand);
			if (node.isLeftChild()) { // LL 
				black(parent);
			} else { // LR
				black(node);
				
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { // R
//			red(grand);
			if (node.isLeftChild()) { // RL
				black(node);
				
				rotateRight(parent);
			} else { // RR
				black(parent);
			}
			rotateLeft(grand);
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node, Node<E> replacement) {
		// 1. 如果删除的是红色节点, 直接删除, 不作任何处理
		if (isRed(node)) return;
		
		// 2. 删除非叶子节点的黑色节点
		// 2.1 删除有2个红色节点的黑色节点
		// 不可能直接被删除, 因为它会找它的子节点代替删除, 所以可以不用考虑
		
		// 2.2 删除有1个红色节点的黑色节点
		// 也就是用于取代node的子节点是红色
		if (isRed(replacement)) {
			black(replacement);
			return;
		}
		
		// 3. 删除的是黑色的叶子节点
		// 3.1 删除根节点
		Node<E> parent = node.parent;
		if (parent == null) return;
		
		//  3.2 非根节点
		// 判断被删除的节点是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left ? parent.right : parent.left;
		if (left) { // 被删除的节点在左边, 兄弟节点在右边
			// 兄弟节点是红色
			if (isRed(sibling)) { 
				black(sibling);
				red(parent);
				
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
				// 这样就会变成兄弟节点是黑色的情况了
			}
			
			// 兄弟节点是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) { // 兄弟节点没有1个红色节点
				// 父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent, null);
				}
			} else { // 兄弟节点至少有一个红色节点, 向兄弟节点借元素
				// 兄弟节点的右边是黑色, 兄弟要先旋转
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				
				// 先染色最后旋转
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateLeft(parent);
			}
		} else { // 被删除的节点在右边, 兄弟节点在左边
			// 兄弟节点是红色
			if (isRed(sibling)) { 
				black(sibling);
				red(parent);
				
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
				// 这样就会变成兄弟节点是黑色的情况了
			}
			
			// 兄弟节点是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) { // 兄弟节点没有1个红色节点
				// 父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent, null);
				}
			} else { // 兄弟节点至少有一个红色节点, 向兄弟节点借元素
				// 兄弟节点的左边是黑色, 兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				
				// 先染色最后旋转
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
	}
	
	/**
	 * 给节点染色
	 * 
	 * @param node 节点
	 * @param color 需要染成什么颜色
	 * @return 返回当前节点
	 */
	private Node<E> color(Node<E> node, boolean color) {
		if (node == null) return node;
		((RBNode<E>)node).color = color;
		return node;
	}
	
	/**
	 * 把节点染成红色
	 */
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}
	
	/**
	 * 把节点染成黑色
	 */
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}
	
	/**
	 * 获取节点的颜色
	 * @param node
	 * @return
	 */
	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>)node).color;
	}
	
	/**
	 * 是否是黑色
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}
	
	/**
	 * 是否是红色
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<>(element, parent);
	}
	
	private static class RBNode<E> extends Node<E> {
		boolean color = RED;
		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}
		
		@Override
		public String toString() {
			String str = "";
			if (color == RED) str = "R_";
			return str + element.toString();
		}
	}
}
