package com.ad;

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
		
		LinkedList<Object> list = new LinkedList<>();
		list.add(11);
		list.add(new Person(11, "tom"));
		list.add(33);
		list.add(null);
		list.add(55);
		
		
		System.out.println(list.contains(55));
		
		list.add(7, 66);
//		System.out.println(list);
		
//		list.remove(4);
		System.out.println(list);
	}

}
