package com.ad.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.ad.printer.BinaryTreeInfo;
import com.ad.printer.BinaryTreeTool;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HashMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	private int size;
	private Node<K, V>[] table;
	private static final int DEFAULT_CAPACITY = 1 << 4;
	
	public HashMap() {
		table = new Node[DEFAULT_CAPACITY];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		if (size == 0) return; 
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	@Override
	public V put(K key, V value) {
		// 获取 key 的索引
		int index = index(key);
		// 取出 index 位置的红黑树根节点
		Node<K, V> root = table[index];
		// 1. 新增节点, 也就是根节点
		if (root == null) {
			root = new Node<>(key, value, null);
			table[index] = root;
			size++;
			afterPut(root);
			return null;
		}
		
		// 添加新的节点到红黑树上面
		// 添加的不是第一个节点
		// 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		K k1 = key;
		int h1 = key == null ? 0 : key.hashCode();
		int cmp = 0;
		Node<K, V> result = null;
		boolean searched = false; // 是否已经搜索过这个Key
		do {
			parent = node;
			
			K k2 = node.key;
			int h2 = node.hash;
			
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (Objects.equals(k1, k2)) {
				cmp = 0;
			} else if (k1 != null && k2 != null
					&& k1.getClass() == k2.getClass()
					&& k1 instanceof Comparable
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
				
			} else if (searched) { // 已经扫描过了
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			} else { // searched == false; 还没有扫描，然后再根据内存地址大小决定左右
				if ((node.left != null && (result = node(node.left, k1)) != null)
						|| (node.right != null && (result = node(node.right, k1)) != null)) {
					// 已经找到这个Key
					node = result;
					cmp = 0;
				} else { // 不存在这个Key
					searched = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}
			
			
			
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { // 自定义对象时, 需要覆盖
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			} 
		} while (node != null);
		
		Node<K, V> newNode = new Node<>(key, value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else if (cmp < 0) {
			parent.left = newNode;
		}
		
		size++;
		
		// 添加新节点之后的处理
		afterPut(newNode);
		
		return null;
	}

	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node != null ? node.value : null;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		if (size == 0) return false;
		
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (Objects.equals(value, node.value)) return true;
				
				if (node.left != null) {
					queue.offer(node.left);
				}
				
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (size == 0 || visitor == null) return;
		
		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (visitor.visit(node.key, node.value)) return;
				
				if (node.left != null) {
					queue.offer(node.left);
				}
				
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
	}
	
	public void print() {
		if (size == 0) return ;
		
		for (int i = 0; i < table.length; i++) {
			// 匿名对象内不能有变量,只能用常量
			Node<K, V> root = table[i];
//			System.out.println("【index = " + i + "】");
			BinaryTreeTool.println(new BinaryTreeInfo() {
				
				@Override
				public Object string(Object node) {
					return node;
				}
				
				@Override
				public Object root() {
					return root;
				}
				
				@Override
				public Object right(Object node) {
					return ((Node<K, V>)node).right;
				}
				
				@Override
				public Object left(Object node) {
					return ((Node<K, V>)node).left;
				}
			});
//			System.out.println("-------------------------");
		}
	}
	
	private V remove(Node<K, V> node) {
		if (node == null) return null;
	
		size--;
		
		V oldValue = node.value;
		
		// 1. 删除度为2的节点
		if (node.hasTwoChildren()) {
			// 前驱节点
			Node<K, V> pre = predecessor(node);
			// 用前驱节点的值覆盖要删除节点的值
			node.key = pre.key;
			node.value = pre.value;
			// 删除前驱节点
			node = pre;
		}
		
		// 删除node节点（node的度必然是1或者0）
		Node<K, V> replacement = node.left != null ? node.left : node.right;
		int index = index(node);
		
		// 2. 删除度为1的节点
		if (replacement != null) {
			// 更改 parent
			replacement.parent = node.parent;
			// 更改 parent 的 left、right 的指向
			if (node.parent == null) { // node 是度为1的节点并且是根节点
				table[index] = replacement;
				// replacement.parent = null; // 因为在更改parent的时候已经设置了
			} else if (node == node.parent.left) { // 左节点
				node.parent.left = replacement;
			} else { // 右节点 : node == node.parent.right
				node.parent.right = replacement;
			}
			
			// 删除节点之后的处理
			afterRemove(replacement);
		} else if (node.parent == null) { // node是叶子节点并且是根节点
			table[index] = null;
			
			// 删除节点之后的处理
			// 如果是根节点,置空之后不作任何处理
//			afterRemove(node);
		} else { // node是叶子节点, 但不是根节点
			if (node == node.parent.left) {
				node.parent.left = replacement;
			} else { // node == node.parent.right
				node.parent.right = replacement;
			}
			
			// 删除节点之后的处理
			afterRemove(node);
		}
		
		return oldValue;
	}
	
	private void afterRemove(Node<K, V> node) {
		// 1. 如果删除的是红色节点, 直接删除, 不作任何处理
		// 这个可以向下合并
//		if (isRed(node)) return;
		
		// 2. 删除非叶子节点的黑色节点
		// 2.1 删除有2个红色节点的黑色节点
		// 不可能直接被删除, 因为它会找它的子节点代替删除, 所以可以不用考虑
		
		// 2.2 删除有1个红色节点的黑色节点
		// 也就是用于取代node的子节点是红色
		if (isRed(node)) {
			black(node);
			return;
		}
		
		// 3. 删除的是黑色的叶子节点
		// 3.1 删除根节点
		Node<K, V> parent = node.parent;
		// 当出现下溢时,根节点没有父类,直接返回就可以了
		if (parent == null) return;
		
		//  3.2 非根节点
		// 判断被删除的节点是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<K, V> sibling = left ? parent.right : parent.left;
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
					afterRemove(parent);
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
					afterRemove(parent);
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
	
	private Node<K, V> predecessor(Node<K, V> node) {
		if (node == null) return null;
		
		Node<K, V> p = null;
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
	
	private void afterPut(Node<K, V> node) {
		// 父节点
		Node<K, V> parent = node.parent;
		
		// 1. 如果是根节点
		if (node.parent == null) {
			black(node);
			return;
		}
		
		// 2. 如果父节点是黑色, 直接返回(因为默认节点是红色)
		if (isBlack(parent)) return;
		
		// 叔父节点
		Node<K, V> uncle = parent.sibling();
		// 祖父节点
		Node<K, V> grand = red(parent.parent);
		
		// 剩下8种情况不符合红黑树性质4
		// 3. uncle 是 红色, 有四种情况, 如果产生上溢, 继续处理
		// 3.1 parent 和 uncle 染成 黑色
		// 3.2 grand 向上合并, 染成 红色, 当做新添加的节点进行处理
		// 3.3. 如果产生上溢, 继续处理
		if (isRed(uncle)) {
			black(parent);
			black(uncle);
			// 把祖父节点当做新添加的节点
//					afterAdd(red(grand));
			afterPut(grand);
			return;
		}
		
		// 4. uncle 不是 RED, 有四种情况
		// 4.1 parent 染成 黑色, grand 染成 红色
		// 4.2 对 grand 进行单旋转, LL : 右旋转, RR : 左旋转
		if (parent.isLeftChild()) { // L
//					red(grand);
			if (node.isLeftChild()) { // LL 
				black(parent);
			} else { // LR
				black(node);
				
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { // R
//					red(grand);
			if (node.isLeftChild()) { // RL
				black(node);
				
				rotateRight(parent);
			} else { // RR
				black(parent);
			}
			rotateLeft(grand);
		}
	}
	
	/**
	 * 通过 key 获取 节点
	 * 
	 * @param key
	 * @return
	 */
	private Node<K, V> node(K key) {
		Node<K, V> root = table[index(key)];
		return root == null ? null : node(root, key);
	}

	private Node<K, V> node(Node<K, V> node, K k1) {
		// k1的哈希值
		int h1 = k1 == null ? 0 : k1.hashCode();
		
		// 存储查询结果
		Node<K, V> result = null;
		int cmp = 0;
		while (node != null) {
			K k2 = node.key;
			int h2 = node.hash;
			// 1. 比较哈希值
			if (h1 > h2) {
				node = node.right;
			} else if (h1 < h2) {
				node = node.left;
			} else if (Objects.equals(k1, k2)) { // 对象相等, hash值一定相等
				return node;
			} else if (k1 != null && k2 != null
					&& k1.getClass() == k2.getClass()
					&& k1 instanceof Comparable
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
				node = cmp > 0 ? node.right : node.left;
			} else if (node.right != null && (result = node(node.right, k1)) != null) {
				return result;
			} else {
				node = node.left;
			}
		}
		
		return null;
	}
	
//	private Node<K, V> node(K key) {
//		Node<K, V> node = table[index(key)];
//		int h1 = key == null ? 0 : key.hashCode();
//		while (node != null) {
//			int cmp = compare(key, node.key, h1, node.hash);
//			if (cmp == 0) return node;
//			if (cmp > 0) {
//				node = node.right;
//			} else { // cmp < 0
//				node = node.left;
// 			}
//		}
//		return null;
//	}

	private Node<K, V> color(Node<K, V> node, boolean color) {
		if (node == null) return node;
		node.color = color;
		return node;
	}
	
	private Node<K, V> red(Node<K, V> node) {
		return color(node, RED);
	}
	
	private Node<K, V> black(Node<K, V> node) {
		return color(node, BLACK);
	}
	
	private boolean colorOf(Node<K, V> node) {
		return node == null ? BLACK : node.color;
	}
	
	private boolean isBlack(Node<K, V> node) {
		return colorOf(node) == BLACK;
	}
	
	private boolean isRed(Node<K, V> node) {
		return colorOf(node) == RED;
	}
	
//	private int compare(K k1, K k2, int h1, int h2) {
//		// 1. 比较哈希值
//		int result = h1 - h2;
//		if (result != 0) return result;
//		
//		// 2. 比较equals
//		if (Objects.equals(k1, k2)) return 0;
//		
//		// 3. 哈希值相等, 但equals不相等
//		if (k1 != null && k2 != null) {
//			// 比较类名
//			String k1Cls = k1.getClass().getName();
//			String k2Cls = k2.getClass().getName();
//			result = k1Cls.compareTo(k2Cls);
//			if (result != 0) return result;
//			
//			// 同一种类型并且具备可比性
//			if (k1 instanceof Comparable) {
//				return ((Comparable) k1).compareTo(k2);
//			}
//		}
//		
//		// 4. 同一种类型, 哈希值相等, 但是 equals 不为 true, 并且不具备可比较性
//		// k1 不为 null, k2 为 null
//		// k2 不为 null, k1 为 null
//		// 通过 key 地址的哈希值比较
//		return System.identityHashCode(k1) - System.identityHashCode(k2);
//	}
	
	private void rotateLeft(Node<K, V> grand) {
		// 需要调整的父节点
		Node<K, V> parent = grand.right;
		// 需要调整的子节点
		Node<K, V> child = parent.left;
		grand.right = parent.left;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	private void rotateRight(Node<K, V> grand) {
		// 需要调整的父节点
		Node<K, V> parent = grand.left;
		// 需要调整的子节点
		Node<K, V> child = parent.right;
		grand.left = parent.right;
		parent.right = grand;
		
		afterRotate(grand, parent, child);
	}
	
	private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
		// 更改parent的父节点
		parent.parent = grand.parent;
		// 更改 grand 父节点的子树指向
		if (grand.isLeftChild()) { // grand 是左节点
			grand.parent.left = parent; 
		} else if (grand.isRightChild()) { // grand 是右节点
			grand.parent.right = parent;
		} else { // grand 是根节点
			table[index(grand)] = parent;
		}
		
		// 更改 child 的父类
		if (child != null) {
			child.parent = grand;
		}
		
		// 更改 grand 的父类
		grand.parent = parent;
	}
	
	/**
	 * 根据key生成对应的索引（在桶数组中的位置）, 也就是hash值
	 */
	private int index(K key) {
		if (key == null) return 0;
		int hash = key.hashCode();
		// 扰动计算
		return (hash ^(hash >>> 16)) & (table.length - 1);
	}
	
	private int index(Node<K, V> node) {
		return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		int hash;
		
		boolean color = RED;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;
		
		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.hash = key == null ? 0 : key.hashCode();
		}
		
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}
		
		public Node<K, V> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}
			
			if (isRightChild()) {
				return parent.left;
			}
			
			return null;
		}

		@Override
		public String toString() {
			return "Node_" + key + "_" + value;
		}
	}
}
