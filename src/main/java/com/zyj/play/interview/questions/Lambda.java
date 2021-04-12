package com.zyj.play.interview.questions;

/**
 * @author zhangyingjie
 * 测试Lambda 底层指定原理
 */
public class Lambda {
    public static void main(String[] args) {
        InterDemo interDemo = System.out::println;
        interDemo.markDemo("WangJun");
    }
}

interface InterDemo {
    void markDemo(String string);
}
