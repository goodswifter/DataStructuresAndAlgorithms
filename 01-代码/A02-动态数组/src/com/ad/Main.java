package com.ad;

public class Main {

	public static void main(String[] args) {
		ArrayList<Object> list = new ArrayList<>();
		
		list.add(11);
		list.add(22);
		list.add(new Person(33, "jack"));
		list.add(null);
		list.add(55);
		list.add(new Person(66, "rose"));
		
		
//		System.out.println(list.set(1, 88));
//		list.remove(2);
//		list.clear();
//		System.out.println(list.indexOf(44));;
//		System.out.println(list.get(3));
//		System.out.println(list.contains(33));
		
		// 单元测试
//		Assert.test(list.get(2) == 44);
		
		System.out.println(list.isEmpty());
		
		System.out.println(list);
	}

}
