package com.mj;

import com.mj.map.HashMap;
import com.mj.map.Map;
import com.mj.map.Map.Visitor;
import com.mj.model.Key;
import com.mj.model.Person;

public class Main {
	
	static void test1() {
		String string = "jack"; // 3254239
		System.out.println(string.hashCode());
//		int len = string.length();
//		int hashCode = 0;
//		for (int i = 0; i < len; i++) {
//			char c = string.charAt(i);
//			hashCode = hashCode * 31 + c;
//			// hashCode = (hashCode << 5) - hashCode + c;
//		}
//		System.out.println(hashCode);
		// hashCode = ((j * 31 + a) * 31 + c) * 31 + k
	}
	
	static void test2() {
		Integer a = 110;
		Float b = 10.6f;
		Long c = 156l;
		Double d = 10.9;
		String e = "rose";
		
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		System.out.println(d.hashCode());
		System.out.println(e.hashCode());
	}

	static void test3() {
		Person p1 = new Person(10, 1.67f, "jack");
		Person p2 = new Person(10, 1.67f, "jack");
		
		Map<Object, Object> map = new HashMap<>();
		map.put(p1, "abc");
		map.put("test", "ccc");
		map.put(p2, "bcd");
		System.out.println(map.size()); 
	}
	
	static void test4() {
		Person p1 = new Person(10, 1.67f, "jack");
		Person p2 = new Person(10, 1.67f, "jack");
		
		Map<Object, Integer> map = new HashMap<>();
		map.put(p1, 1);
		map.put(p2, 2);
		map.put("jack", 3);
		map.put("rose", 4);
		map.put("jack", 5);
		map.put(null, 6);

//		System.out.println(map.size());
//		System.out.println(map.remove("jack"));
//		System.out.println(map.get("jack"));
//		System.out.println(map.size());
		
		System.out.println(map.containsKey(p1));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsValue(6));
		System.out.println(map.containsValue(1));
		
//		map.traversal(new Visitor<Object, Integer>() {
//			public boolean visit(Object key, Integer value) {
//				System.out.println(key + "_" + value);
//				return false;
//			}
//		});

//		System.out.println(map.get("jack"));
//		System.out.println(map.get("rose"));
//		System.out.println(map.get(null));
//		System.out.println(map.get(p1));
	}
	
	static void test5() {
		Person p1 = new Person(10, 1.67f, "jack");
		Person p2 = new Person(10, 1.67f, "jack");
		
		Map<Object, Integer> map = new HashMap<>();
		map.put(p1, 1);
		map.put(p2, 2);
		map.put("jack", 3);
		map.put("rose", 4);
		map.put("jack", 5);
		map.put(null, 6);

//		System.out.println(map.size());
//		System.out.println(map.remove("jack"));
//		System.out.println(map.get("jack"));
//		System.out.println(map.size());
		
		System.out.println(map.containsKey(p1));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsValue(6));
		System.out.println(map.containsValue(1));
		
//		map.traversal(new Visitor<Object, Integer>() {
//			public boolean visit(Object key, Integer value) {
//				System.out.println(key + "_" + value);
//				return false;
//			}
//		});

//		System.out.println(map.get("jack"));
//		System.out.println(map.get("rose"));
//		System.out.println(map.get(null));
//		System.out.println(map.get(p1));
	}
	
	public static void main(String[] args) { 
		HashMap<Object, Integer> map = new HashMap<>();
		for (int i = 1; i <= 19; i++) {
			map.put(new Key(i), i);
		}
//		map.traversal(new Visitor<Object, Integer>() {
//			public boolean visit(Object key, Integer value) {
//				System.out.println(key + "_" + value);
//				return false;
//			}
//		});
		
		System.out.println(map.get(new Key(1)));
		map.print();
	}

}
