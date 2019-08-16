package com.ad;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static void test1() {
		Trie<Integer> trie = new Trie<>();
		trie.add("cat", 1);
		trie.add("dog", 2);
		trie.add("catalog", 3);
		trie.add("cast", 4);
		trie.add("小码哥", 5);
		AssertUtil.test(trie.size() == 5);
		AssertUtil.test(trie.startsWith("do"));
		AssertUtil.test(trie.startsWith("c"));
		AssertUtil.test(trie.startsWith("ca"));
		AssertUtil.test(trie.startsWith("cat"));
		AssertUtil.test(trie.startsWith("cata"));
		AssertUtil.test(!trie.startsWith("hehe"));
		AssertUtil.test(trie.get("小码哥") == 5);
//		Asserts.test(trie.remove("cat") == 1);
//		Asserts.test(trie.remove("catalog") == 3);
//		Asserts.test(trie.remove("cast") == 4);
//		Asserts.test(trie.size() == 2);
//		Asserts.test(trie.startsWith("do"));
//		Asserts.test(!trie.startsWith("c"));
	}

}
