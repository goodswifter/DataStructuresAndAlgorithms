package Others;

/**
 * 
 * @author goodswifter
 * https://leetcode-cn.com/problems/fibonacci-number/
 */
public class _509_斐波那契数 {
	
	public static void main(String[] args) {
		System.out.println(fib(7));
	}

	/*
	 * 菲波那切数列 : fibonacci number
	 * 0 1 1 2 3 5 8 13 ...
	 * N > 1
	 */
	public static int fib(int N) {
		if (N < 0) return 0;
		
		if (N <= 1) return N;
		int first = 0;
		int second = 1;
		for (int i = 2; i <= N; i++) {
			second += first;
			first = second - first;
		}
		
		return second;
	}
	

	// 方式一:
	public static int fib1(int n) {
		if (n < 0) return 0;
		
		if (n <= 1) return n;
		
		return fib1(n - 1) + fib1(n - 2);
	}
}
