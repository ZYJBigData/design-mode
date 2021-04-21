package com.zyj.play.interview.questions.thirdquarter.javaee;

/**
 * @author zhangyingjie
 */
public class StringDemo {
    public static void main(String[] args) {
        String str="58tongcheng";
        String str1=new StringBuilder().append("58").append("tongcheng").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1==str1.intern());

        String str2=new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        System.out.println(str2== str2.intern());
    }

}
