package com.zyj.play.interview.questions.aop.jdk;

/**
 * 需要代理的实际对象
 *
 * @author zhangyingjie
 */
public class RealSubject implements Subject {
    private String test;

    public RealSubject() {
        System.out.println("没有参数的构造函数。。。");
    }

    public RealSubject(String name) {
        System.out.println("name===" + name);
    }

    private RealSubject(String name, Integer a) {
        System.out.println("name==" + name + ",a==" + a);
    }

    private void verifyMethod(String  name) {
        System.out.println("私有的普通方法被调用了。。=="+name);
    }

    @Override
    public final String SayHello(String name) {
        return "你好 " + name;
    }

    @Override
    public String SayGoodBye() {
        return " 拜拜 ";
    }

    public void getTest() {
        System.out.println("test 值是：" + test);
    }
}
