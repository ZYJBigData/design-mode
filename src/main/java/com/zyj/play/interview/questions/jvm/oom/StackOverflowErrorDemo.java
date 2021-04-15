package com.zyj.play.interview.questions.jvm.oom;

/**
 * @author zhangyingjie
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }
    public static void stackOverflowError(){
        stackOverflowError();//Exception in thread "main" java.lang.StackOverflowError
    }
}
