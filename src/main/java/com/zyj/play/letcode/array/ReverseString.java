package com.zyj.play.letcode.array;

/**
 * @author zhangyingjie
 */
public class ReverseString {
    public static void main(String[] args) {
        char[] a = {'h', 'e', 'l', 'l', 'o'};
        char[] b = {'H', 'a', 'n', 'n', 'a', 'h'};
        ReverseString reverseString = new ReverseString();
        reverseString.reverseString(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left <= right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}
