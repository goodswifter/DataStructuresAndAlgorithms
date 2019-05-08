package Stack;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 * 
 * @author goodswifter
 *
 */
public class _150_逆波兰表达式求值 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String item : tokens) {
			if (isOperator(item)) {
				int a = stack.pop();
				int b = stack.pop();
		
				stack.push(calculate(item, a, b));
			} else {
				stack.push(Integer.parseInt(item));
			}
		}
        
        return stack.pop();
    }
    
    /**
     * 计算两个数的值
     * 
     * @param operator 运算符
     * @param a 第一个数
     * @param b 第二个数
     * @return 结果
     */
    private int calculate(String operator, int a, int b) {
		int result = 0;
		switch (operator) {
		case "+":
			result = (a + b);
			break;
			
		case "-":
			result = (a - b);
			break;
			
		case "*":
			result = (a * b);
			break;
			
		case "/":
			result = (a / b);
			break;
		}
		
		return result;
    }
    
    /**
     * 是不是运算符
     */
    private boolean isOperator(String str) {
    	
    	Set<String> set = new HashSet<>();
    	set.add("+");
    	set.add("-");
    	set.add("*");
    	set.add("/");
    	
		return set.contains(str);
	}
}
