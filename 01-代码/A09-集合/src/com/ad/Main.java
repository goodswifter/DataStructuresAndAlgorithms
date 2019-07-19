package com.ad;

import com.ad.set.ListSet;
import com.ad.set.Set;
import com.ad.set.Set.Visitor;
import com.ad.tree.RBTree;

public class Main {

	public static void main(String[] args) {
		test1Set();
	}
	
	/**
	 * ListSet
	 */
	public static void test1Set() {
		
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
