package com.zyj.play.letcode;

/**
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * <p>
 * 本题解题思路运用递归的方式进行
 *
 * @author zhangyingjie
 */
public class Search {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
//        int[] nums = {-1, 0, 3};
        int target = 0;
        int search = search(nums, target);
        System.out.println("result=={}" + search);
    }

    public static int search(int[] nums, int target) {
        return recursionBinarySearch(nums, target, 0, nums.length - 1);
    }

    public static int recursionBinarySearch(int[] arr, int key, int low, int high) {
        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (arr[middle] > key) {
            //比关键字大则关键字在左区域
            return recursionBinarySearch(arr, key, low, middle - 1);
        } else if (arr[middle] < key) {
            //比关键字小则关键字在右区域
            return recursionBinarySearch(arr, key, middle + 1, high);
        } else {
            return middle;
        }
    }
}
