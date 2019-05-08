package LinkedList;

public class _876_链表的中间结点 {
	public ListNode middleNode(ListNode head) {
		ListNode[] A = new ListNode[100];
        
		int t = 0;
        while (head != null) {
            A[t++] = head;
            head = head.next;
        }
        
        return A[t / 2];
    }
}
