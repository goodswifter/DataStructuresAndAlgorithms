package Stack;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 * 
 * @author goodswifter
 *
 */
public class _150_逆波兰表达式求值 {
	
	public static void main(String[] args) {
		String[] tokens = {"2", "1", "-", "3", "*"};
 		System.out.println(evalRPN(tokens));
	}
	
	public static int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
        	switch (token) {
				case "+" : stack.push(stack.pop() + stack.pop()); break;
				case "-" : {
		        	int right = stack.pop();
		        	int left = stack.pop();
					stack.push(left - right);
					break;
				}
				case "*" : stack.push(stack.pop() * stack.pop()); break;
				case "/" : {
		        	int right = stack.pop();
		        	int left = stack.pop();
		        	stack.push(left / right);
		        	break;
				}
				default  : stack.push(Integer.parseInt(token));
			}
        }
		
		return stack.pop();
    }
	
	public static int evalRPN2(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
        
        for (String item : tokens) {
        	stack.push(isOperator(item) 
        			? stack.push(calculate(item, stack.pop(), stack.pop()))
        			: stack.push(Integer.parseInt(item)));
        }
		
		return stack.pop();
    }
	
    public int evalRPN1(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String item : tokens) {
			if (isOperator(item)) {
				// 要先取出右操作数, 再取出左操作数
//				int right = stack.pop();
//				int left = stack.pop();
//		
//				stack.push(calculate(item, left, right));
				stack.push(calculate(item, stack.pop(), stack.pop()));
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
     * @param left 左操作数
     * @param right 右操作数
     * @return 结果
     */
    private static int calculate(String operator, int right, int left) {
		int result = 0;
		switch (operator) {
		case "+":
			result = (left + right);
			break;
			
		case "-":
			result = (left - right);
			break;
			
		case "*":
			result = (left * right);
			break;
			
		case "/":
			result = (left / right);
			break;
		}
		
		return result;
    }
    
    /**
     * 是不是运算符
     */
    private static boolean isOperator(String str) {
    	return "+-*/".contains(str);
	}
}
