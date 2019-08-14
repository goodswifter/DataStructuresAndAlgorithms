package Set;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-arrays/
 * 
 * 给定两个数组，编写一个函数来计算它们的交集
 * 
 * @author goodswifter
 *
 */
public class _349_两个数组的交集 {

	public static void main(String[] args) {
		int[] nums1 = {1, 2, 5, 5, 4, 3, 1};
		int[] nums2 = {1, 2, 1};
		
		int[] res = intersection(nums1, nums2);
		System.out.println(Arrays.toString(res));
	}
	
	static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for (int i = 0; i < nums1.length; i++) {
			map.put(nums1[i], i);
		}
        
        for (int i = 0; i < nums2.length; i++) {
			if (map.containsKey(nums2[i])) {
				set.add(nums2[i]);
			}
		}
    
        // 将整型set 转成整型数组
        int[] res = new int[set.size()];
        Object[] temps = set.toArray();
        for (int i = 0; i < temps.length; i++) {
			res[i] = (int)temps[i];
		}
	
		return res;
    }
}
