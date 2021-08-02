package com.zyj.play.interview.questions.aop.cglib;

/**
 * @author zhangyingjie
 */
public class ProxyFactory {
    public static AmethodClass newCGLibProxy() {
        return CGLibProxy.getInstance().getProxy(AmethodClass.class);
    }
}
