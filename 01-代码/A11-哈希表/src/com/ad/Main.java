package com.ad;

import com.ad.file.FileInfo;
import com.ad.file.FileTool;
import com.ad.map.HashMap;
import com.ad.model.Key;
import com.ad.model.SubKey1;
import com.ad.model.SubKey2;
import com.ad.tool.AssertUtil;
import com.ad.tool.TimeUtil;
import com.ad.tool.TimeUtil.Task;

public class Main {
	public static void main(String[] args) {
		System.out.println("---");
		test1();
//		test2(new HashMap<>());
//		test3(new HashMap<>());
//		test4(new HashMap<>());
//		test5(new HashMap<>());
		System.out.println("---");
	}
	
	static void test1() {
		String filepath = "/Users/jingshi/Documents/zhong";
		FileInfo fileInfo = FileTool.read(filepath, 
				new String[]{"class", "java"});
		String[] words = fileInfo.words();

		System.out.println("总行数：" + fileInfo.getLines());
		System.out.println("单词总数：" + words.length);
		System.out.println("-------------------------------------");
		
//		java.util.HashMap<String, Integer> map = new java.util.HashMap<>();
//		
//		for (String word : words) {
//			Integer count = map.get(word);
//			count = count == null ? 0 : count;
//			map.put(word, count + 1);
//		}
//		System.out.println(map.size()); // 963
		
		HashMap<String, Integer> map = new HashMap<>();
		TimeUtil.test(map.getClass().getName(), new Task() {
			@Override
			public void execute() {
				for (String word : words) {
					Integer count = map.get(word);
					count = count == null ? 0 : count;
					map.put(word, count + 1);
				}
				System.out.println(map.size()); // 963
				
//				int count = 0;
//				for (String word : words) {
//					Integer i = map.get(word);
//					count += i == null ? 0 : i;
//					map.remove(word);
//				}
//				AssertUtil.test(count == words.length);
//				AssertUtil.test(map.size() == 0);
			}
		});
	}
	
	static void test2(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			map.put(new Key(i), i + 5);
		}
		AssertUtil.test(map.size() == 20);
		AssertUtil.test(map.get(new Key(4)) == 4);
		AssertUtil.test(map.get(new Key(5)) == 10);
		AssertUtil.test(map.get(new Key(6)) == 11);
		AssertUtil.test(map.get(new Key(7)) == 12);
		AssertUtil.test(map.get(new Key(8)) == 8);
	}
	
	static void test3(HashMap<Object, Integer> map) {
		map.put(null, 1); // 1
		map.put(new Object(), 2); // 2
		map.put("jack", 3); // 3
		map.put(10, 4); // 4
		map.put(new Object(), 5); // 5
		map.put("jack", 6);
		map.put(10, 7);
		map.put(null, 8);
		map.put(10, null);
		AssertUtil.test(map.size() == 5);
		AssertUtil.test(map.get(null) == 8);
		AssertUtil.test(map.get("jack") == 6);
		AssertUtil.test(map.get(10) == null);
		AssertUtil.test(map.get(new Object()) == null);
		AssertUtil.test(map.containsKey(10));
		AssertUtil.test(map.containsKey(null));
		AssertUtil.test(map.containsValue(null));
		AssertUtil.test(map.containsValue(1) == false);
	}
	
	static void test4(HashMap<Object, Integer> map) {
		map.put("jack", 1);
		map.put("rose", 2);
		map.put("jim", 3);
		map.put("jake", 4);		
		for (int i = 1; i <= 10; i++) {
			map.put("test" + i, i);
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			AssertUtil.test(map.remove(new Key(i)) == i);
		}
		for (int i = 1; i <= 3; i++) {
			map.put(new Key(i), i + 5);
		}
		AssertUtil.test(map.size() == 21);
		AssertUtil.test(map.get(new Key(1)) == 6);
		AssertUtil.test(map.get(new Key(2)) == 7);
		AssertUtil.test(map.get(new Key(3)) == 8);
		AssertUtil.test(map.get(new Key(4)) == 4);
		AssertUtil.test(map.get(new Key(5)) == null);
		AssertUtil.test(map.get(new Key(6)) == null);
		AssertUtil.test(map.get(new Key(7)) == null);
		AssertUtil.test(map.get(new Key(8)) == 8);
	}
	
	static void test5(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new SubKey1(i), i);
		}
		map.put(new SubKey2(1), 5);
		AssertUtil.test(map.get(new SubKey1(1)) == 5);
		AssertUtil.test(map.get(new SubKey2(1)) == 5);
		AssertUtil.test(map.size() == 20);
	}
}
