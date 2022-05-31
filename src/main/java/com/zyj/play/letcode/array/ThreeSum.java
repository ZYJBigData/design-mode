package com.zyj.play.letcode.array;

import java.util.*;

public class ThreeSum {
    public static void main(String[] args) {
//        int[] nums = {3,0,-2,-1,1,2};
//        ThreeSum threeSum = new ThreeSum();
//        List<List<Integer>> lists = threeSum.threeSum(nums);
//        System.out.println(lists);
        String str="{\"id\":\"001\",\"query\":[{\"id\":\"001\",\"name\":\"yang,zijiang\",\"age\":\"12\"}]}";
        System.out.println(str);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int first = 0; first < n; ++first) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            int third = n - 1;
            int target = -nums[first];
            for (int second = first + 1; second < n; ++second) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }
}
