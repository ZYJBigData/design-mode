package com.zyj.play.interview.questions.aop.s;

/**
 * 静态代理
 */
public class Client {
    public static void main(String[] args) {
        Service realService = new RealServiceImpl();
        Service proxy = new ProxyServiceImpl(realService);
        proxy.operate();
    }
}
