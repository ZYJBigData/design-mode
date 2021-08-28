package com.zyj.play.letcode.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 异位词 指字母相同，但排列不同的字符串
 *
 * @author zhangyingjie
 */
public class FindAnagrams {
    public static void main(String[] args) {
//        String s = "cbaebabacd";
//        String p = "abc";
        String s = "abab";
        String p = "ab";
        FindAnagrams findAnagrams = new FindAnagrams();
        List<Integer> anagrams = findAnagrams.findAnagrams(s, p);
        System.out.println("anagrams==={}" + anagrams);

    }

    public List<Integer> findAnagrams(String s, String p) {
        int left = 0;
        int right = 0;
        int windowLength;
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>(p.length());
        for (Character c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        while (right < s.length()) {
            if (map.containsKey(s.charAt(right))) {
                map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) - 1);
            }
            right++;
            while (checkIndex(map)) {
                windowLength = right - left;
                if (windowLength == map.size() || windowLength == p.length()) {
                    result.add(left);
                }
                if (map.containsKey(s.charAt(left))) {
                    map.put(s.charAt(left), map.getOrDefault(s.charAt(left), 0) + 1);
                }
                left++;
            }
        }
        return result;
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
