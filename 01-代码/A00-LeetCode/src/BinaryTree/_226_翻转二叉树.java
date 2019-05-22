package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 * 
 * @author goodswifter
 *
 */
public class _226_翻转二叉树 {
	// 1. 前序遍历
//	public TreeNode invertTree(TreeNode root) {
//		if (root == null) return root;
//		
//		TreeNode node = root.left;
//		root.left = root.right;
//		root.right = node;
//		
//        invertTree(root.left);
//        invertTree(root.right);
//        
//        return root;
//    }
	
	// 2. 中序遍历
//	public TreeNode invertTree(TreeNode root) {
//		if (root == null) return root;
//		
//        invertTree(root.left);
//		
//		TreeNode node = root.left;
//		root.left = root.right;
//		root.right = node;
//		
//        invertTree(root.left);
//        
//        return root;
//    }
	
	// 3. 后序遍历
//	public TreeNode invertTree(TreeNode root) {
//		if (root == null) return root;
//		
//		TreeNode node = root.left;
//		root.left = root.right;
//		root.right = node;
//		
//        invertTree(root.left);
//        invertTree(root.right);
//        
//        return root;
//    }
	
	// 4. 层序遍历
	public TreeNode invertTree(TreeNode root) {
		if (root == null) return root;
		
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			TreeNode tmp = node.left;
			node.left = node.right;
			node.right = tmp;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
        
        return root;
    }
}
