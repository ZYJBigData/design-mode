package com.zyj.play.interview.questions.thirdquarter.javaee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangyingjie
 * 给一个数组和一个目标值,查出数组中两个值的和，与目标值相等，并返回数组中这样两个值的下标。
 * 可以假设每次输入只对应一个答案。
 */
public class TwoNumSum {
    public static void main(String[] args) {
        System.out.println(towSum1());
        System.out.println(towSum2());
    }

    public static List<Integer> towSum1() {
        int[] num = new int[]{2, 7, 11, 15};
        int target = 9;
        int sum;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num.length; j++) {
                sum = num[i] + num[j];
                if (sum == target) {
                    list.add(i);
                    list.add(j);
                    return list;
                }
            }
        }
        return list;
    }

    public static List<Integer> towSum2() {
        int[] num = new int[]{2, 7, 11, 15};
        int target = 9;
        List<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> numMap = new HashMap<>(4);
        for (int i = 0; i < num.length; i++) {
            int sub = target - num[i];
            if (numMap.containsKey(sub)) {
                list.add(numMap.get(sub));
                list.add(i);
                return list;
            }
            numMap.put(num[i], i);
        }
        return list;
    }
}
