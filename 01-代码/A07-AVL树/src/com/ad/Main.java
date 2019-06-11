package com.ad;

import com.ad.printer.BinaryTreeTool;
import com.ad.tree.AVLTree;

public class Main {

	public static void main(String[] args) {
		test1AVL();
	}
	
	public static void test1AVL() {
		
		Integer[] datas = new Integer[] {
			12, 54, 28, 63, 59, 66, 48, 14, 68, 29, 79, 24, 52
		};
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < datas.length; i++) {
			avl.add(datas[i]);
		}
		BinaryTreeTool.println(avl);
	}

}
