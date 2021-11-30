package com.zyj.play.letcode.array;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * @author zhangyingjie
 */
public class RemoveDuplicates {

    public static void main(String[] args) {
//        int[] nums = {1, 1};
        int[] nums = {1, 1, 2};
//        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        RemoveDuplicates removeDuplicates = new RemoveDuplicates();
        int result = removeDuplicates.removeDuplicates(nums);
        System.out.println("result=={}" + result);
        for (int i = 0; i < result; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    public int removeDuplicates(int[] nums) {
        int slow = 0;
        int fast = 1;
        int sum = 0;
        int index = 0;
        while (fast < nums.length) {
            if (nums[slow] == nums[fast]) {
                fast++;
                continue;
            }
            nums[++index] = nums[fast];
            sum += fast - slow - 1;
            slow = fast;
        }
        sum += fast - slow - 1;
        return nums.length - sum;
    }
}
