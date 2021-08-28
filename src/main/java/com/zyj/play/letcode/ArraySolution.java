package com.zyj.play.letcode;

/**
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * 要求时间复杂度为O(n)。
 *
 * @author zhangyingjie
 */
public class ArraySolution {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = maxSubArray(nums);
        System.out.println("result==={}" + result);
    }

    public static int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int j = 1; j < nums.length; j++) {
            if (dp[j - 1] > 0) {
                dp[j] = dp[j - 1] + nums[j];
            } else {
                dp[j] = nums[j];
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }
}
