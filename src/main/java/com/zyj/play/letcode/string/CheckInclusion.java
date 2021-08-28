package com.zyj.play.letcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。
 * <p>
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 *
 * @author zhangyingjie
 */
public class CheckInclusion {
    public static void main(String[] args) {
        CheckInclusion checkInclusion = new CheckInclusion();
        String s1 = "ab";
        String s2 = "eidbaoo";
//        String s1 = "abcdxabcde";
//        String s2 = "abcdeabcdx";
        boolean result = checkInclusion.checkInclusion(s1, s2);
        System.out.println("result==={}" + result);
    }

    public boolean checkInclusion(String s1, String s2) {
        int left = 0;
        int right = 0;
        int windowLength;
        Map<Character, Integer> map = new HashMap<>(s1.length());
        for (Character c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        while (right < s2.length()) {
            if (map.containsKey(s2.charAt(right))) {
                map.put(s2.charAt(right), map.getOrDefault(s2.charAt(right), 0) - 1);
            }
            right++;
            while (checkIndex(map)) {
                windowLength = right - left;
                if (windowLength == map.size() || windowLength == s1.length()) {
                    return true;
                }
                if (map.containsKey(s2.charAt(left))) {
                    map.put(s2.charAt(left), map.getOrDefault(s2.charAt(left), 0) + 1);
                }
                left++;
            }
        }
        return false;
    }

    public boolean checkIndex(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 0) {
                return false;
            }
        }
        return true;
    }


}
