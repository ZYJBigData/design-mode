package com.zyj.play.interview.questions.aop.cglib;

import com.zyj.play.interview.questions.aop.jdk.RealSubject;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

@Slf4j
public class Client {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        //继承被代理类
        enhancer.setSuperclass(RealSubject.class);
        //设置回调
        enhancer.setCallback(new HelloMethodInterceptor());
        //设置代理类对象
        RealSubject realSubject = (RealSubject) enhancer.create();
        //在调用代理类中方法时会被我们实现的方法拦截器进行拦截
//        System.out.println(realSubject.SayHello("张英杰"));
        System.out.println(realSubject.SayGoodBye());
    }
}