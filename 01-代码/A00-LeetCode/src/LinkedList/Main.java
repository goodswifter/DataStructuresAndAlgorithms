package LinkedList;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		LinkedList<ListNode> list = new LinkedList<>();
		ListNode firstNode = new ListNode(1);
		list.add(firstNode);
		list.add(new ListNode(1));
		list.add(new ListNode(2));
		list.add(new ListNode(5));
		list.add(new ListNode(5));
		list.add(new ListNode(3));
		
		
		LinkedList<ListNode> list1 = deleteDuplicates(firstNode);
		System.out.println(list1);
	}
	
	public static LinkedList<ListNode> deleteDuplicates(ListNode head) {
    	if (head == null) return null;
    	ListNode currentNode = head;
    	
    	LinkedList<ListNode> list = new LinkedList<>();
    	
    	while (currentNode != null && currentNode.next != null) {
			if (currentNode.next.val == currentNode.val) {
				currentNode.next = currentNode.next.next;
			} else {
				currentNode = currentNode.next;
			}
			list.add(currentNode.next);
		}
    	
    	return list;
    }

}
