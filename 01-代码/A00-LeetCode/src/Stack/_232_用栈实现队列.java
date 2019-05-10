package Stack;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/submissions/
 * 
 * 思路:
 * 1. 准各2个栈: inStack、 outStack
 * 2. 入队时，push到inStack中
 * 3. 出队时
 * 	  > 如果outStack空, 将inStack所有元素逐一弹出， push到outStack, outStack弹出栈顶元素
 * 	  > 如果outStack不为空，outStack贪出栈顶元素
 * 
 * @author goodswifter
 *
 */
public class _232_用栈实现队列 {
	/** 入队栈 */
	private Stack<Integer> inStack;
	/** 出队栈 */
	private Stack<Integer> outStack;
	
	/** Initialize your data structure here. */
    public _232_用栈实现队列() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }
    
    /** 入队. */
    public void push(int x) {
        inStack.push(x);
    }
    
    /** 出队. */
    public int pop() {
    	handleOutStack();
        
        return outStack.pop();
    }
    
    /** 获取队头元素. */
    public int peek() {
        handleOutStack();
        
        return outStack.peek();
    }
    
    /** 是否为空. */
    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
    
    /** 处理出队栈 */
    public void handleOutStack() {
    	if (outStack.isEmpty()) {
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
		}
	}
}
