package com.zyj.play.letcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 */
public class TwoSumI {
    Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        int[] nums = {3, 1, 3, 6};
        TwoSumI twoSumI = new TwoSumI();
        int[] ints = twoSumI.twoSum(nums, 6);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i] + " ");
        }
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key=: " + entry.getKey() + "value=: " + entry.getValue());
        }
        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            if (map.containsKey(other) && map.get(other) != i) {
                return new int[]{i, map.get(other)};
            }
        }
        return new int[]{-1, -1};
    }
}
