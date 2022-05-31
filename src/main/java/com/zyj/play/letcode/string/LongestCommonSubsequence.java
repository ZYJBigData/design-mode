package com.zyj.play.letcode.string;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 *
 * @author zhangyingjie
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
//        String text1 = "bsbininm";
//        String text2 = "jmjkbkjkv";
//        String text1="abcde";
//        String text2="ace";
//        String text1 = "oxcpqrsvwf";
//        String text2 = "shmtulqrypy";
//        String text1="abcabcbb";
//        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
//        int result = longestCommonSubsequence.longestCommonSubsequence(text1, text2);
//        int result = longestCommonSubsequence.lengthOfLongestSubstring(" ");
//        System.out.println("result=={}" + result);

        String str="id,name,age\n" + "001,\"yang,zijiang\",12";
        System.out.println(str);
    }

//    //动态规划
//    public int longestCommonSubsequence(String text1, String text2) {
//        int m = text1.length();
//        int n = text2.length();
//        int[][] dp = new int[m + 1][n + 1];
//        for (int i = 1; i <= m; i++) {
//            for (int j = 1; j <= n; j++) {
//                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
//                    dp[i][j] = dp[i - 1][j - 1] + 1;
//                } else {
//                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
//                }
//            }
//        }
//        return dp[m][n];
//    }

    public int lengthOfLongestSubstring(String s) {
        int maxLength=0;
        int start=0;
        int end=0;
        Set<Character> set=new HashSet();
        while(end <s.length()){
            char c = s.charAt(end);
            if(!set.contains(c)){
                set.add(c);
            }else{
                start++;
                end=start;
                if(maxLength<set.size()){
                    maxLength=set.size();
                }
                set.clear();
            }
            end=end+1;
        }
        return maxLength;
    }
}
