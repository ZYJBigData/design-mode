package com.zyj.play.interview.questions.aop.cglib;

/**
 * @author zhangyingjie
 * 如果 Spring 识别到所代理的类没有实现 Interface，那么就会使用 CGLib 来创建动态代理，原理实际上成为所代理类的子类。
 */
public class TestMain {
    public static void main(String agrs[]) {
        AmethodClass amethodClass = ProxyFactory.newCGLibProxy();
        amethodClass.a();
        amethodClass.b();
    }
}
