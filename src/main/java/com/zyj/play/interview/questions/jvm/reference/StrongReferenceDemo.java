package com.zyj.play.interview.questions.jvm.reference;
/**
 * @author zhangyingjie
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2= new Object();
        obj1 = null;
        System.gc();
        System.out.println(obj2);
        System.out.println(obj1);
    }
}
