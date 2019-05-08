package com.ad;

import com.ad.circlelinkedlist.CircleLinkedList2;
import com.ad.circlelinkedlist.List2;
import com.ad.AssertUtil;;

public class Main {

	public static void main(String[] args) {
//		ArrayList<Object> list = new ArrayList<>();
//		
//		list.add(11);
//		list.add(22);
//		list.add(null);
//		list.add(55);
//
//		list.remove(3);
//		
//		System.out.println(list);
		
//		LinkedList<Object> list = new LinkedList<>();
//		list.add(11);
//		list.add(33);
//		list.add(55);
		
		
//		System.out.println(list.contains(55));
		
//		list.add(7, 66);
//		System.out.println(list);
		
//		list.remove(4);
//		System.out.println(list);
		
//		System.out.println(fun(5));
		
//		ArrayList2<Object> list2 = new ArrayList2<>();
//		for (int i = 0; i < 50; i++) {
//			list2.add(i);
//		}
//		
//		for (int i = 0; i < 50; i++) {
//			list2.remove(0);
//		}

//		testList(new CircleLinkedList<Integer>());
//		testList(new LinkedList<Integer>());
		
		// 约瑟夫问题
		testJosephus(new CircleLinkedList2<Integer>());
	}
	
	static void testJosephus(List2<Integer> list) {
		for (int i = 0; i < 50; i++) {
			list.add(i);
		}
		
		while (list.size() > 1) {
			for (int i = 0; i < 4; i++) {
				list.next();
			}
			Integer element = list.remove();
			System.out.println(element);
		}
		
		System.out.println(list);
	}
	
	static void testList(List2<Integer> list) {
		list.add(11);
		list.add(22);
		list.add(33);
		list.add(44);

		list.add(0, 55); // [55, 11, 22, 33, 44]
		list.add(2, 66); // [55, 11, 66, 22, 33, 44]
		list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

		list.remove(0); // [11, 66, 22, 33, 44, 77]
		list.remove(2); // [11, 66, 33, 44, 77]
		list.remove(list.size() - 1); // [11, 66, 33, 44]
		
		AssertUtil.test(list.indexOf(44) == 3);
		AssertUtil.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
		AssertUtil.test(list.contains(33));
		AssertUtil.test(list.get(0) == 11);
		AssertUtil.test(list.get(1) == 66);
		AssertUtil.test(list.get(list.size() - 1) == 44);
		
		System.out.println(list);
	}
	
	public static int fun(int n) {
		if(n == 1) return 1;

		System.out.println("111");
		int m = fun(n - 1) + n;
		System.out.println(n);
		
		return m;
	}

}
