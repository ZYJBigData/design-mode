package com.zyj.play.interview.questions.flink.fanshe;

import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.consumer.internals.SubscriptionState;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.IsolationLevel;
import org.apache.kafka.common.utils.LogContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        test();
    }

    public static void test() {
        SubscriptionState subscriptionState = new SubscriptionState(new LogContext(), OffsetResetStrategy.EARLIEST);
        TopicPartition topicPartition = new TopicPartition("zyj-in", 0);

        ClassLoader classLoader = SubscriptionState.class.getClassLoader();
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass("org.apache.kafka.clients.consumer.internals.SubscriptionState");
            Method method = clazz.getDeclaredMethod("partitionLag", TopicPartition.class, IsolationLevel.class);
            method.setAccessible(true);
            Long result = (Long) method.invoke(subscriptionState, topicPartition, IsolationLevel.READ_UNCOMMITTED);
            System.out.println(method.getName());
            System.out.println(result);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("捕捉到异常。。。。");
            e.printStackTrace();
        }

    }

    public void test1() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class clazz = classLoader.loadClass("com.zyj.play.interview.questions.flink.fanshe.Cat");
            Cat cat = (Cat) clazz.newInstance();
            Field field = clazz.getDeclaredField("weight");
            field.setAccessible(true);
            field.setInt(cat, 10);
            System.out.println("weight===={}" + field.getInt(cat));
            Method method = clazz.getDeclaredMethod("eat", String.class);
            method.setAccessible(true);
            method.invoke(cat, "hello word");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
