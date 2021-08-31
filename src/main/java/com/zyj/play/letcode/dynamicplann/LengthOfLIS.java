package com.zyj.play.letcode.dynamicplann;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 * @author zhangyingjie
 */
public class LengthOfLIS {
    public static void main(String[] args) {
//        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] nums = {0, 1, 0, 3, 2, 3};
//        int[] nums = {7, 7, 7, 7, 7, 7, 7};
//        int[] nums = {-2, -1};
//        int[] nums = {3, 1, 2};
//        int[] nums = {4, 10, 4, 3, 8, 9};
//        int[] nums = {1, 3, 6, 7, 9, 4, 10, 5, 6, 7, 8, 9};
        LengthOfLIS lengthOfLIS = new LengthOfLIS();
        int result = lengthOfLIS.lengthOfLIS(nums);
        System.out.println("result=={}" + result);
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
