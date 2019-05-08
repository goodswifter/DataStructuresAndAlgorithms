package com.mj;

import com.mj.file.Files;
import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;
import com.mj.tree.AVLTree;
import com.mj.tree.BST;
import com.mj.tree.BinaryTree;
import com.mj.tree.BinaryTree.Visitor;

@SuppressWarnings("unused")
public class Main {

	static void test1() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
		}
		
		BinaryTrees.println(avl);
	}
	
	public static void main(String[] args) {
		test1();
	}
}
