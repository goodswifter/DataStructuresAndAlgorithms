package 链表;

public class _206_反转链表 {
	
	// [1, 2, 3, 4] -> [4, 3, 2, 1]
	// 四个节点分别是head1, head2, head3, head4
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) return head;
	
		// 找到新的头节点
		ListNode newHead = reverseList(head.next);

		// 交换两个节点
		head.next.next = head;
		head.next = null;
		
		return newHead;
    }

	// 递归分析
//	public ListNode reverseList11(ListNode head) {
//		if (head == null || head.next == null) return head;
//	
//		// head 1
//		ListNode newHead = { 
//				// head2 
//				ListNode newHead = {
//						// head3
//						ListNode newHead = reverseList11(ListNode head);
//
//						head3.next.next = head3;
//						head3.next = null;
//						
//						return newHead;
//				};;
//				
//				head2.next.next = head2;
//				head2.next = null;
//				
//				return newHead;
//		};
//		
//		head1.next.next = head1;
//		head1.next = null;
//		
//		return newHead;
//    }

	
	// 非递归方式
	public ListNode reverseList2(ListNode head) {
		if (head == null || head.next == null) return head;
	
		ListNode newHead = null;
		while (head != null) {
			ListNode tmp = head.next;
			head.next = newHead;
			newHead = head;
			head = tmp;
		}
		
		return newHead;
    }

}
