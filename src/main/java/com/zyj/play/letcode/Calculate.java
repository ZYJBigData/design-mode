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
        int result = calculate(s3);
        System.out.println(result);
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
}
