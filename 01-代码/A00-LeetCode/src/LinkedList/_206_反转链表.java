package LinkedList;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * @author goodswifter
 *
 */
public class _206_反转链表 {
	
	// 递归方式
	public ListNode reverseList(ListNode head) {
		
//		// 空链表
//		if (head == null) return null;
//		// 只有一个元素的链表
//		if (head.next == null) return head;
		// 合并判断就是
		if (head == null || head.next == null) return head;
		
		ListNode newHead = reverseList(head.next);
		
		// 让下一个节点的next指向自己
		head.next.next = head;
		// 让自己的节点指向null
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
	
	// 非递归方式 : 迭代
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
