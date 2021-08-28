package com.zyj.play.letcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * //滑动窗口的概念
 *
 * @author zhangyingjie
 */
public class MinWindow {
    public static void main(String[] args) {
        MinWindow minWindow = new MinWindow();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String s1 = minWindow.minWindow(s, t);
        System.out.println("result=={}" + s1);
    }

    public String minWindow(String s, String t) {
        //左指针
        int left = 0;
        //右指针
        int right = 0;
        //匹配到字符串开始的位置
        int strStart = 0;
        //用来记录最小的窗口值
        int windowLength = Integer.MAX_VALUE;
        Map<Character, Integer> map = new HashMap<>(t.length());
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        while (right < s.length()) {
            if (map.containsKey(s.charAt(right))) {
                map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) - 1);
            }
            right++;
            //检查窗口是否把t中字符全部覆盖了，如果覆盖了，要移动窗口的左边界
            //找到最小的能全部覆盖的窗口
            while (check(map)) {
                //如果现在窗口比之前保存的还要小，就更新窗口的长度
                //以及窗口的起始位置
                if (right - left < windowLength) {
                    windowLength = right - left;
                    strStart = left;
                }
                //移除窗口最左边的元素，也就是缩小窗口
                char leftChar = s.charAt(left);
                if (map.containsKey(leftChar)) {
                    map.put(leftChar, map.getOrDefault(leftChar, 0) + 1);
                }
                //左指针往右移
                left++;
            }
        }
        if (windowLength != Integer.MAX_VALUE) {
            return s.substring(strStart, strStart + windowLength);
        }
        return "";
    }

    //检查窗口是否把字符串t中的所有字符都覆盖了，如果map中所有
    // value的值都不大于0，则表示全部覆盖
    private boolean check(Map<Character, Integer> map) {
        for (int value : map.values()) {
            //注意这里的value是可以为负数的，为负数的情况就是，相同的字符右
            // 指针扫描的要比t中的多，比如t是"ABC"，窗口中的字符是"ABBC"
            if (value > 0) {
                return false;
            }
        }
        return true;
    }
}
