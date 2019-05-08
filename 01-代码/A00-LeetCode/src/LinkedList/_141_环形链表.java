package LinkedList;

/**
 * 
 * https://leetcode-cn.com/problems/linked-list-cycle/
 * @author goodswifter
 *
 */
public class _141_环形链表 {
	public boolean hasCycle(ListNode head) {
		if (head == null || head.next == null) return false;
		
		// 通过快慢指针方式判断, 如果快指针和慢指针相遇, 就说明有环, 否则就说明没有环
		ListNode slow = head;
		ListNode fast = head.next;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			
			if (slow == fast) return true;
		}
		
		return false;
    }
}
