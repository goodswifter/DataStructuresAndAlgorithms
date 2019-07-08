package com.ad;

import com.ad.printer.BinaryTreeTool;
import com.ad.tree.AVLTree;
import com.ad.tree.RBTree;

public class Main {

	public static void main(String[] args) {
		test1RBTree();
	}
	
	public static void test1RBTree() {
		
		Integer[] datas = new Integer[] {
				93, 18, 85, 91, 78, 60, 90, 81, 72, 12, 26, 86, 76, 73, 67, 69
		};
		
		RBTree<Integer> rbTree = new RBTree<>();
		for (int i = 0; i < datas.length; i++) {
			rbTree.add(datas[i]);
		}
		BinaryTreeTool.println(rbTree);
	}

}
