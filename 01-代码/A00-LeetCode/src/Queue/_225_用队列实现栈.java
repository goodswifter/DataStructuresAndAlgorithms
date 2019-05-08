package Queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * https://leetcode-cn.com/problems/implement-stack-using-queues/comments/
 * 
 * 思路:
 * 1. 准各2个队列: queueA、 queueB
 * 2. 入栈时，push 到 queueA 中
 * 3. 出栈时 将 队列A 中的前size-1个元素逐一入队到 队列B 中, A中最后一个就是 pop 时的值
 * 
 * @author goodswifter
 *
 */
public class _225_用队列实现栈 {
	private Queue<Integer> queueA;
	private Queue<Integer> queueB;
	
	/** Initialize your data structure here. */
	public _225_用队列实现栈() {
        queueA = new LinkedList<>();
        queueB = new LinkedList<>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        queueA.add(x);
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        while (queueA.size() > 1) {
			queueB.add(queueA.poll());
		}
        return queueB.poll();
    }
    
    /** Get the top element. */
    public int top() {
    	while (queueA.size() > 1) {
			queueB.add(queueA.poll());
		}
    	return queueA.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queueA.isEmpty() && queueB.isEmpty();
    }
}
