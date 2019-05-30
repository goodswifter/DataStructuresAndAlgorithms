package com.ad;

import java.util.Comparator;
import com.ad.printer.BinaryTreeTool;
import com.ad.tree.BST;
import com.ad.file.FileTool;
import com.ad.printer.BinaryTreeInfo;

public class Main {

	public static void main(String[] args) {
		test5();
	}
	
	public static void test5() {
		
		Integer[] datas = new Integer[] {
//			7, 4, 9, 2, 5, 8, 11, 1, 3, 10, 12
			7, 4, 9, 2, 5, 11
//			1, 2, 3, 4, 5, 8, 10, 11, 12
		};
		
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < datas.length; i++) {
			bst.add(datas[i]);
		}
//		for (int i = 0; i < 20; i++) {
//			bst.add((int)(Math.random() * 100));
//		}
		
		// 前序遍历 : 7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
//		bst.preorderTraversal();

		// 中序遍历(升序) : 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12
		// 中序遍历(升序) : 12, 11, 10, 9, 8, 7, 5, 4, 3, 2, 1
//		bst.inorderTraversal();
		
//		bst.levelOrderTraversal(new Visitor<Integer>() {
//			
//			@Override
//			public void visit(Integer element) {
//				System.out.println("_" + (element + 3) + "_");
//			}
//		});
		BinaryTreeTool.print(bst);
		System.out.println(bst.isComplete());
	}
	
	
	public static void test4() {
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < 40; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		String str = BinaryTreeTool.printString(bst);
		str += "\n";
		FileTool.writeToFile("/Users/jingshi/Desktop/1.txt", str, true);
	}
	
	public static void test3() {
		BinaryTreeTool.println(new BinaryTreeInfo() {
			
			@Override
			public Object string(Object node) {
				return node.toString() + "_";
			}
			
			@Override
			public Object root() {
				return "A";
			}
			
			@Override
			public Object right(Object node) {
				if (node.equals("A")) return "C";
				if (node.equals("C")) return "E";
				return null;
			}
			
			@Override
			public Object left(Object node) {
				if (node.equals("A")) return "B";
				if (node.equals("C")) return "D";
				return null;
			}
		});
	}
	
	public static void test2() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BST<Person> bst1 = new BST<>(new PersonComparator1());
		for (int i = 0; i < data.length; i++) {
			bst1.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst1);
		System.out.println("\n");
		
		BST<Person> bst2 = new BST<>(new PersonComparator2());
		for (int i = 0; i < data.length; i++) {
			bst2.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst2);
		System.out.println("\n");
		
		BST<Person> bst3 = new BST<>();
		for (int i = 0; i < data.length; i++) {
			bst3.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst3);
		System.out.println("\n");
		
		BST<Person> bst4 = new BST<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		for (int i = 0; i < data.length; i++) {
			bst4.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst4);
		System.out.println("\n");
		
		
	}
	
	public static void test1() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTreeTool.print(bst);
	}
	
	private static class PersonComparator1 implements Comparator<Person> {
		public int compare(Person p1, Person p2) {
			return p1.getAge() - p2.getAge();
		}
	}
	
	private static class PersonComparator2 implements Comparator<Person> {
		public int compare(Person p1, Person p2) {
			return p2.getAge() - p1.getAge();
		}
	}

}
