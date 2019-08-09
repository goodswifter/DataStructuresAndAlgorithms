package com.ad;

import com.ad.set.ListSet;
import com.ad.set.Set;
import com.ad.set.Set.Visitor;
import com.ad.TimeTool.Task;
import com.ad.file.FileInfo;
import com.ad.file.FileTool;
import com.ad.set.TreeSet;

public class Main {

	public static void main(String[] args) {
		test3ReadFiles();
	}
	
	public static void test3ReadFiles() {
		FileInfo fileInfo = FileTool.read("/Users/jingshi/Documents/zhong/DSAL", 
				new String[]{"class", "java"});
		
		System.out.println("文件数量：" + fileInfo.getFiles());
		System.out.println("代码行数：" + fileInfo.getLines());
		String[] words = fileInfo.words();
		System.out.println("单词数量：" + words.length);
		
//		TimeTool.test("ListSet", new Task() {
//			public void execute() {
//				testSet(new ListSet<>(), words);
//			}
//		});
//		
//		TimeTool.test("TreeSet", new Task() {
//			public void execute() {
//				testSet(new TreeSet<>(), words);
//			}
//		});
	}
	
	static void testSet(Set<String> set, String[] words) {
		for (int i = 0; i < words.length; i++) {
			set.add(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.contains(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.remove(words[i]);
		}
	}
	
	/**
	 * ListSet
	 */
	public static void test2TreeSet() {
		
		Set<Integer> treeSet = new TreeSet<>();
		treeSet.add(12);
		treeSet.add(10);
		treeSet.add(7);
		treeSet.add(11);
		treeSet.add(10);
		treeSet.add(11);
		treeSet.add(9);
		
		treeSet.traversal(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return element == 10;
			}
		});
	}
	
	/**
	 * ListSet
	 */
	public static void test1ListSet() {
		
		Integer[] datas = new Integer[] {
				93, 18, 85, 93, 78, 60
		};
		
		Set<Integer> listSet = new ListSet<>();
		
		for (int i = 0; i < datas.length; i++) {
			listSet.add(datas[i]);
		}
		
		listSet.traversal(new Visitor<Integer>() {
			
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}
}
