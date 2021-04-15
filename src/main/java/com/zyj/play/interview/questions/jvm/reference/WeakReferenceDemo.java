package com.zyj.play.interview.questions.jvm.reference;

import java.lang.ref.WeakReference;

/**
 * @author zhangyingjie
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> o2 = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(o2.get());
        o1 = null;

        System.gc();
        System.out.println("=========================");
        System.out.println(o1);
        System.out.println(o2.get());
    }
}
