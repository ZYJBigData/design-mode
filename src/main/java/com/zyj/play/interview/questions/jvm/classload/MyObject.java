package com.zyj.play.interview.questions.jvm.classload;


/**
 * @author zhangyingjie
 */
public class MyObject {
    public static void main(String[] args) throws InterruptedException {
//        Object object = new Object();
//        System.out.println(object.getClass().getClassLoader());
//        System.out.println(object.getClass().getClassLoader().getParent());
//        System.out.println(object.getClass().getClassLoader().getParent().getParent());
//        System.out.println("***************************************'");
//
//        MyObject myObject = new MyObject();
//        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());
//        System.out.println(myObject.getClass().getClassLoader().getParent());
//        System.out.println(myObject.getClass().getClassLoader());

        String s1 = new String("a") + new String("b");
        s1.intern();
        String s2 = "ab";
        System.out.println(s1 == s2);//false
        Thread.sleep(1000000);

    }
}
