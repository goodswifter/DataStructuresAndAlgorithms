package LinkedList;

/**
 * 
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 * 
 * @author goodswifter
 *
 */
public class _203_移除链表元素 {
    public ListNode removeElements(ListNode head, int val) {
    	if (head == null) return null;
    	
    	ListNode newHead = new ListNode(0);
    	newHead.next = head;
    	ListNode currentNode = newHead;
    	
    	while (currentNode.next != null) {
//    		System.out.println(currentNode.val);
    		
			if (currentNode.next.val == val) {
				currentNode.next = currentNode.next.next;
			} else {
				currentNode = currentNode.next;
			}
		}
    	
    	return newHead.next;
    }
}
