package Stack;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/score-of-parentheses/submissions/
 * 
 * @author goodswifter
 *
 */
public class _856_括号的分数 {
	public int scoreOfParentheses(String S) {
        int sum = 0;
        
        Stack<Character> stack = new Stack<>();
        
        int length = S.length();
        // 是不是最里面的括号, 是的话就需要计算
        boolean isInnermost = false;
        for (int i = 0; i < length; i++) {
			char c = S.charAt(i);
			if (c == '(') {
				stack.push(c);
				isInnermost = true;
			} else {
				if (isInnermost) { // 是最里面的, 直接计算值
					sum += Math.pow(2, stack.size() - 1);
					stack.pop();
					isInnermost = false;
				} else { // 不需要计算, 直接pop
					stack.pop();
				}
			}
		}
        
        return sum;
    }
}
