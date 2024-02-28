package com.zyj.play.interview.questions.aop;

import com.zyj.play.interview.questions.aop.jdk.RealSubject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<RealSubject> aClass = RealSubject.class;
//        constructorReflect(aClass);
//        fieldReflect(aClass);
        methodReflect(aClass);
    }


    public static void constructorReflect(Class<RealSubject> aClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<RealSubject> constructor = aClass.getDeclaredConstructor(String.class, Integer.class);
        constructor.setAccessible(true);
        RealSubject realSubject = constructor.newInstance("张大英", 30);
    }

    public static void fieldReflect(Class<RealSubject> aClass) throws NoSuchFieldException, IllegalAccessException {
        Field test = aClass.getDeclaredField("test");
        test.setAccessible(true);
        RealSubject realSubject = new RealSubject();
        test.set(realSubject, "我成功赋值了");
        realSubject.getTest();
    }

    public static void methodReflect(Class<RealSubject>aClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method verifyMethod = aClass.getDeclaredMethod("verifyMethod",String.class);
        verifyMethod.setAccessible(true);
        Object verifyMethod1 = verifyMethod.invoke(aClass.newInstance(),"verifyMethod");
        System.out.println(verifyMethod1);
    }
}
