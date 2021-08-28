package com.zyj.play.letcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyingjie
 * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
 */
public class ContainsNearbyDuplicate {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        int k = 3;
//        int[] nums = {1, 0, 1, 1};
//        int k = 1;
//        int[] nums = {1, 2, 3, 1, 2, 3};
//        int k = 2;
        boolean result = containsNearbyDuplicate(nums, k);
        System.out.println("result=={}" + result);
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) != null && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
