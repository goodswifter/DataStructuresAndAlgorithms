package com.mj;

import java.util.HashMap;
import java.util.Map;

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

	public static void main(String[] args) { 
		Person p1 = new Person(10, 1.67f, "jack");
		Person p2 = new Person(10, 1.67f, "jack");
		
		Map<Object, Object> map = new HashMap<>();
		map.put(p1, "abc");
		map.put("test", "ccc");
		map.put(p2, "bcd");
		System.out.println(map.size()); 
	}

}
