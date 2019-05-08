package LinkedList;

public class ListNode {

	int val;
	ListNode next;
	
	ListNode(int x) { 
		val = x; 
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(val);
		return builder.toString();
	}	

}
