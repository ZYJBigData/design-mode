package com.zyj.play.letcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zhangyingjie
 * //通过两个栈实现一个计算器 逆波兰式方式进行计算会比较简单
 */
public class Calculate {
    public static void main(String[] args) {
        String s = "1 + 1";
        String s1 = " 2-1 + 2 ";
        String s2 = "(1+(4+5+2)-3)+(6+8)";
        String s3 = "-(3+(4+5))";
//        int result = calculate(s3);
//        boolean palindrome = isPalindrome(12);
        int i = climbStairs(6);
        System.out.println(i);
//        System.out.println(palindrome);
//        System.out.println(result);
    }

    public static int calculate(String s) {
        Deque<Integer> ops = new LinkedList<>();
        ops.push(1);
        int sign = 1;
        int ret = 0;
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }

    public static boolean isPalindrome(int x) {
        String xStr = x + "";
        int left = 0;
        int right = xStr.length() - 1;
        while (right - left >= 1) {
            if (xStr.charAt(left) == xStr.charAt(right)) {
                right--;
                left++;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int climbStairs(int n) {
        //这里大小根据自己需要，或者使用 List 也可以
        int[] dp = new int[100000];
        dp[1] = 1;
        dp[2] = 2;
        for( int i = 3;i <= n;++i ){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
