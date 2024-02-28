package com.zyj.play.letcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * @author zhangyingjie
 */
public class MoveZeroes {
    public static void main(String[] args) {
//        int[] nums = {0,1,0,3,12};
//        int[] nums = {1};
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
//        int[] nums={1,0,1};
//        MoveZeroes moveZeroes = new MoveZeroes();
//        moveZeroes.moveZeroes(nums);
//        for (int i = 0; i < nums.length; i++) {
//            System.out.print(nums[i] + " ");
//        }
//        moveZeroes.reverse(1);
        System.out.println(search(nums, 0));
    }

    //二分查找
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        return digui(nums, left, right, target);

    }
    
    public static int findMinIndex(int[] nums){
        Arrays.asList(nums).sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1];
            }
        });
    } 

    public static int digui(int[] nums, int left, int right, int target) {
        if (left>=right){
            return -1;
        }
        int mid = (left + right) / 2;
        if (nums[mid] == target) {
            return mid;
        }
        digui(nums, mid + 1, right, target);
        digui(nums, left, mid, target);
        return -1;
    }

    public void moveZeroes(int[] nums) {
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0 && nums[slow] == 0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;
                slow++;
            }
            if (nums[slow] != 0) {
                slow++;
            }
            fast++;
        }
    }

    public int reverse(int x) {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("****************");
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                System.out.println("结束了。。。");
            }
        }
    }
}
