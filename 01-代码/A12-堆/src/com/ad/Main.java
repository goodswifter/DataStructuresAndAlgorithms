package com.ad;

import java.util.Comparator;

import com.ad.heap.BinaryHeap;
import com.ad.printer.BinaryTreeTool;

public class Main {
	public static void main(String[] args) {
		test4();
	}
	
	static void test4() {
		// 1. 新建一个小顶堆
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		
		// 找出最大的前k个数
		int k = 3;
		Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93, 
						91, 19, 54, 47, 73, 62, 76, 63, 35, 18, 
						90, 6, 65, 49, 3, 26, 61, 21, 48};
		
		for (int i = 0; i < data.length; i++) {
			Integer element = data[i];
			if (heap.size() < k) {
				heap.add(element);
			} else if (element > heap.get()) {
				heap.replace(element);
			}
		}
		
		// 复杂度 : O(nlogk)
		BinaryTreeTool.println(heap);
	}
	
	static void test3() {
		Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
		BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		
		BinaryTreeTool.println(heap);
	}
	
	static void test2() {
		Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
		BinaryHeap<Integer> heap = new BinaryHeap<>(data);
		BinaryTreeTool.println(heap);
		
		data[0] = 10;
		data[1] = 20;
		BinaryTreeTool.println(heap);
	}
	
	static void test1() {
		BinaryHeap<Integer> heap = new BinaryHeap<>();
		heap.add(50);
		heap.add(30);
		heap.add(20);
		heap.add(60);
		heap.add(55);
		BinaryTreeTool.println(heap);

//		heap.remove();
//		BinaryTreeTool.println(heap);
		System.out.println(heap.replace(35));
		BinaryTreeTool.println(heap);
	}
}
