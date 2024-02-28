package com.zyj.play.interview.questions.aop.cglib;

import com.zyj.play.interview.questions.aop.jdk.RealSubject;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {
    RealSubject target = new RealSubject();

    /**
     * @param proxy 代理类对象
     * @param method 当前正在执行的原对象方法
     * @param args 方法参数
     * @param methodProxy 代理对象方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before: " + method.getName());
//        Object object = method.invoke(target, args); //用反射方法调用目录target
//        Object invoke = methodProxy.invoke(target, args);//内部没有用反射，结合目标对象target
        Object object = methodProxy.invokeSuper(proxy, args);//内部没有用反射，需要代理对象proxy
        System.out.println("After: " + method.getName());
        return object;
    }
}