package com.zyj.play.interview.questions.aop.jdk;

import java.lang.reflect.Proxy;

/**
 * @author zhangyingjie
 */
public class DynamicProxyDemonstration {
    public static void main(String[] args) {
        //代理的真是对象
        Object realSubject = new RealSubject();
     
        Subject subject = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), new InvocationHandlerImpl(realSubject));
        System.out.println("动态代理对象的类型：" + subject.getClass().getName());
        System.out.println("---------------------------------------------------------");
        String hello = subject.SayHello("jiankunking");
        System.out.println(hello);
//        String goodbye = subject.SayGoodBye();
//        System.out.println(goodbye);
    }
}
