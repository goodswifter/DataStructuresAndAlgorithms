package LinkedList;

/**
 * 
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 * 
 * @author goodswifter
 *
 */
public class _83_删除排序链表中的重复元素 {
    public ListNode deleteDuplicates(ListNode head) {
    	if (head == null) return null;
    	ListNode currentNode = head;
    	
    	while (currentNode != null && currentNode.next != null) {
			if (currentNode.next.val == currentNode.val) {
				currentNode.next = currentNode.next.next;
			} else {
				currentNode = currentNode.next;
			}
		}
    	
    	return head;
    }
}
