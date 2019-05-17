package com.ad;

import java.util.Comparator;

import com.ad.printer.BinaryTreeTool;
import com.ad.file.FileTool;
import com.ad.printer.BinaryTreeInfo;

public class Main {

	public static void main(String[] args) {
		test4();
	}
	
	public static void test4() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
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
		
		BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new PersonComparator1());
		for (int i = 0; i < data.length; i++) {
			bst1.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst1);
		System.out.println("\n");
		
		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator2());
		for (int i = 0; i < data.length; i++) {
			bst2.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst2);
		System.out.println("\n");
		
		BinarySearchTree<Person> bst3 = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst3.add(new Person(data[i]));
		}
		
		BinaryTreeTool.print(bst3);
		System.out.println("\n");
		
		BinarySearchTree<Person> bst4 = new BinarySearchTree<>(new Comparator<Person>() {

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
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
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
