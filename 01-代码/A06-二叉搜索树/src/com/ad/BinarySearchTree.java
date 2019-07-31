package com.ad;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.ad.printer.BinaryTreeInfo;

@SuppressWarnings({"unchecked", "unused"})
public class BinarySearchTree<E> implements BinaryTreeInfo {
	private int size;
	private Node<E> root;

	private Comparator<E> comparator;
	
	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		root = null;
		size = 0;
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		
		// 添加第一个节点
		if (root == null) {
			root = new Node<>(element, null);
			size++;
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
		
		Node<E> newNode = new Node<>(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else if (cmp < 0) {
			parent.left = newNode;
		}
		
		size++;
	}
	
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
		} else { // node是叶子节点, 但不是根节点
			if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { // node == node.parent.right
				node.parent.right = replacement;
			}
		}
	}
	
	/**
	 * 前驱节点:中序遍历时的前一-个节点
	 * 如果是二叉搜索树，前驱节点就是前一个比它小的节点
	 * 
	 * 
	 * 分析 : 
	 * 1. node.left != null
	 * predecessor = node.left.right.right.right...
	 * 终止条件: right 为 null
	 * 
	 * 2. node.left == nul1 & node.parent != null
	 * predecessor = node.parent.parent.parent...
	 * 终止条件: node 在parent的右子树中
	 * 
	 * 3. node.left == null && node.parent == null
	 * 那就没有前驱节点
	 * 举例:没有左子树的根节点
	 * 
	 */
	private Node<E> predecessor(Node<E> node) {
		if (root == null) return null;
		
		Node<E> p = null;
		if (node.left != null) {
			p = node.left;
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		
		// node.parent == null
		// node = node.parent.right
		return node.parent;
	}
	
	/**
	 * 
	 * 后继节点:中序遍历时的后一个节点
	 * 如果是二叉搜索树，后继节点就是后一个比它大的节点
	 * 
	 * 
	 * node.right != null 
	 * successor = node.right.left.left.left...
	 * 终止条件: left 为 null
	 * 
	 * node.right == null && node.parent != null
	 * successor = node.parent.parent.parent...
	 * 终止条件: node 在parent的左子树中.
	 * 
	 * node.right == null && node.parent == null
	 * 那就没有前驱节点
	 * 举例:没有右子树的根节点
	 */
	private Node<E> successor(Node<E> node) {
		if (node == null) return null;
		
		// 前驱节点在左子树当中（right.left.left.left....）
		Node<E> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		
		// 从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}

		return node.parent;
	}
	
	/**
	 * 判断一棵树是否为完全二叉树
	 * 
	 * 思路: 
	 * 
	 * 1. 如果树为空，返回false
	 * 
	 * 2. 如果树不为空，开始层序遍历二叉树(用队列)
	 * 	2.1 如果 node.left != null && node.right != null, 将node.left、 node.right 按顺序入队
	 * 	2.2 如果 node.left == null && node.right != null, 返回false
	 * 	2.3 如果 node.left != null && node.right == null 或者 node.left == null && node.right == null
	 * 		2.3.1 那么后面遍历的节点应该都为叶子节点，才是完全二叉树
	 * 		2.3.2 否则返回false 
	 * 	2.4 遍历结束，返回true
	 * 
	 */
	public boolean isComplete() {
		if (root == null) return false;
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		
		// 需不需要判断剩余的节点都是叶子节点
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			
			if (leaf && !node.isLeaf()) return false;
			
			if (node.left != null) {
				queue.offer(node.left);
			} else if (node.right != null) { // node.left == null && node.right != null
				return false;
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			} else { // node.right == null
				// node.left != null && node.right == null
				// node.left == null && node.right == null
				leaf = true;
			}
		}
		
		return leaf;
	}
	
	// 这是一种错误方式
	public boolean isComplete2() {
		if (root == null) return false;
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		
		// 需不需要判断剩余的节点都是叶子节点
		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			
			if (leaf && !node.isLeaf()) return false;
			
			// 7, 4, 9, 2, 1 就会判断不出来
			if (node.left != null && node.right != null) {
				queue.offer(node.left);
				queue.offer(node.right);
			} else if (node.left == null && node.right != null) 
				return false;
			else {
				leaf = true;
			}
		}
		
		return leaf;
	}
	
	/**
	 * 二叉树的高度 : 迭代方式
	 */
	public int height() {
		if (root == null) return 0;
		
		// 树的高度
		int height = 0;
		// 存储着每一层的元素个数
		int levelSize = 1; // 默认第一层, 也就是root节点
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			levelSize--;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
			
			if (levelSize == 0) { // 意味着即将访问下一层
				levelSize = queue.size();
				height++;
			}
		}
		
		return height;
	}
	
	/**
	 * 二叉树的高度 : 递归方式
	 */
	public int height2() {
		return height(root);
	}
	
	private int height(Node<E> node) {
		if (node == null) return 0;
		
		return Math.max(height(node.left), height(node.right)) + 1;
	}
	
	/**
	 * 外界提供方法, 如何打印这个元素
	 */
	public static abstract class Visitor<E> {
		boolean stop;
		/**
		 * @return 如果返回true, 就代表停止遍历
		 */
		abstract boolean visit(E element);
	}
	
	/**
	 * 前序遍历
	 */
	public void preorderTraversal(Visitor<E> visitor) {
		if (visitor == null) return;
		preorderTraversal(root, visitor);
	}
	
	public void preorderTraversal() {
		preorderTraversal(root, new Visitor<E>() {
			boolean visit(E element) {
				System.out.println(element);
				return true;
			}
		});
	}
	
	private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;
		
		visitor.stop = visitor.visit(node.element);
		preorderTraversal(node.left, visitor);
		preorderTraversal(node.right, visitor);
	}
	
	/**
	 * 中序遍历
	 */
	public void inorderTraversal(Visitor<E> visitor) {
		if (visitor == null) return;
		inorderTraversal(root, visitor);
	}
	
	public void inorderTraversal() {
		inorderTraversal(root, new Visitor<E>() {
			boolean visit(E element) {
				System.out.println(element);
				return true;
			}
		});
	}
	
	// 中序遍历分升序和降序两种
	private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
		// 这里面要判断 visitor.stop 是因为防止它继续向下走
		if (node == null || visitor.stop) return;
		
		inorderTraversal(node.left, visitor);
		// 如果是停止, 直接结束
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
		inorderTraversal(node.right, visitor);
	}
	
	/**
	 * 后序遍历
	 */
	public void postorderTraversal(Visitor<E> visitor) {
		if (visitor == null) return;
		postorderTraversal(root, visitor);
	}
	
	public void postorderTraversal() {
		postorderTraversal(root, new Visitor<E>() {
			boolean visit(E element) {
				System.out.println(element);
				return true;
			}
		});
	}
	
	private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;
		
		postorderTraversal(node.left, visitor);
		postorderTraversal(node.right, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
	}
	
	/**
	 * 层序遍历
	 * 
	 * 思路 : 
	 * 1. 将根节点入队
	 * 2. 循环执行以下操作，直到队列为空
	 * 		将队头节点A出队，进行访问
	 * 		将A的左子节点入队
	 * 		将A的右子节点入队
	 */
	public void levelOrderTraversal(Visitor<E> visitor) {
		if (root == null || visitor == null) return;
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (visitor.visit(node.element)) return;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	public void levelOrderTraversal() {
		levelOrderTraversal(new Visitor<E>() {
			boolean visit(E element) {
				System.out.println(element);
				return true;
			}
		});
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

	@Override
	public Object root() {
		return root;
	}

	@Override
	public Object left(Object node) {
		return ((Node<E>)node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>)node).right;
	}

	@Override
	public Object string(Object node) {
		Node<E> n = (Node<E>)node;
		String parentString = "null";
		if (n.parent != null) {
			parentString = n.parent.element.toString();
		}
		
		return n.element + "_p(" + parentString + ")";
	}
	
	private static class Node<E> {
		E element;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		
		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}
		
		// 是否有左右子树
		public boolean isHasTwoChild() {
			return left != null && right != null;
		}
		
		// 是否是叶子节点
		public boolean isLeaf() {
			return left == null && right == null;
		}
	}
	
	/**
	 * 利用前序遍历树状打印二叉树
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(root, sb, "...");
		return sb.toString();
	}
	
	private void toString(Node<E> node, StringBuilder sb, String prefix) {
		if (node == null) return;
		
		sb.append(prefix).append("【").append(node.element).append("】").append("\n");
		toString(node.left, sb, prefix + "〖 L 〗");
		toString(node.right, sb, prefix + "〖 R 〗");
	}
}
