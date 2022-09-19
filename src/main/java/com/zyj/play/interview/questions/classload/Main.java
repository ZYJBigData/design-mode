package com.zyj.play.interview.questions.classload;

public class Main {
    public static void main(String[] args) {
//        静态加载
        if ("A".equals(args[0])) {
            A a = new A();
            a.start();
        }
        if ("B".equals(args[0])) {
            B b = new B();
            b.start();
        }
        //动态加载
        try {
            Class c = Class.forName(args[0]);
            All a = (All) c.newInstance();
            a.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
