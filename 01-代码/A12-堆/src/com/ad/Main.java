package com.ad;

import com.ad.heap.BinaryHeap;
import com.ad.printer.BinaryTreeTool;

public class Main {
	public static void main(String[] args) {
		BinaryHeap<Integer> heap = new BinaryHeap<>();
		heap.add(50);
		heap.add(30);
		heap.add(20);
		heap.add(60);
		heap.add(55);
		BinaryTreeTool.print(heap);
	}
}
