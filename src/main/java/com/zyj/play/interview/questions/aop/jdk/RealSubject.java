package com.zyj.play.interview.questions.aop.jdk;

/**
 * 需要代理的实际对象
 *
 * @author zhangyingjie
 */
public class RealSubject implements Subject {
    @Override
    public final String SayHello(String name) {
        return "你好 " + name;
    }

    @Override
    public String SayGoodBye() {
        return " 拜拜 ";
    }
}
