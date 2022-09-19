package com.zyj.play.interview.questions.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before: " + method.getName());
        Object object = proxy.invokeSuper(o, args);
        System.out.println("After: " + method.getName());
        return object;
    }
}