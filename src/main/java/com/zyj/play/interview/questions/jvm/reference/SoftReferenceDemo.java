package com.zyj.play.interview.questions.jvm.reference;

import java.lang.ref.SoftReference;

/**
 * @author zhangyingjie
 */
public class SoftReferenceDemo {
    public void softMemoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> o2 = new SoftReference<>(o1);
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(o2.get());
    }

    public void softMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> o2 = new SoftReference<>(o1);
        o1 = null;
        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(o2.get());
        }
    }

    public static void main(String[] args) {
        SoftReferenceDemo softReferenceDemo = new SoftReferenceDemo();
//        softReferenceDemo.softMemoryEnough();
        softReferenceDemo.softMemoryNotEnough();
    }
}
