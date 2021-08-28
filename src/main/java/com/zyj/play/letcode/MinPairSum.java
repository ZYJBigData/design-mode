package com.zyj.play.letcode;

import java.util.Arrays;

/**
 * @author zhangyingjie
 * 一个数对 (a,b) 的 数对和 等于 a + b 。最大数对和 是一个数对数组中最大的 数对和 。
 * <p>
 * 比方说，如果我们有数对 (1,5) ，(2,3) 和 (4,4)，最大数对和 为 max(1+5, 2+3, 4+4) = max(6, 5, 8) = 8 。
 * 给你一个长度为 偶数 n 的数组 nums ，请你将 nums 中的元素分成 n / 2 个数对，使得：
 * <p>
 * nums 中每个元素 恰好 在 一个 数对中，且
 * 最大数对和 的值 最小 。
 * 请你在最优数对划分的方案下，返回最小的 最大数对和 。
 */
public class MinPairSum {
    public static void main(String[] args) {
//        int[] nums = {3, 5, 2, 3};
        int[] nums = {3, 5, 4, 2, 4, 6};
        int result = minPairSum(nums);
        System.out.println("result==={}" + result);
    }

    public static int minPairSum(int[] nums) {
        int[] copyNums = new int[nums.length / 2];
        Arrays.sort(nums);
        for (int i = 0; i < nums.length / 2; i++) {
            copyNums[i] = nums[i] + nums[nums.length - i - 1];
        }
        return Arrays.stream(copyNums).max().getAsInt();
    }
}
