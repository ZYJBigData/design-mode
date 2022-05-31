package com.zyj.play.letcode.string;

/**
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * @author zhangyingjie
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        String s = "babad";
//        String s="cbbd";
//        String s="a";
//        String s="ac";
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        String result = longestPalindrome.longestPalindrome(s);
        System.out.println("result=={}" + result);
    }

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        int begin = 0;
        //s.charAt(i)每次都检查数组下标越界，因此先转换成字符数组，这一步必须
        char[] charArray = s.toCharArray();
        //枚举所有长度大于1的子串
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && validPalindrome(charArray, i, j)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    public boolean validPalindrome(char[] charArray, int left, int right) {
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
