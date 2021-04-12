package com.zyj.play.interview.questions.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhangyingjie
 * ArrayList 是线程不安全 线程安全的方式
 * 验证arrayList线程不安全的例子
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        //ConcurrentModificationException
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        /**
         * 解决方案
         * 那么三种构建集合线程安全
         * 1.new Vector   每个方法都加锁
         * 2.Collections.synchronizedList  给集合加锁
         * 3.new CopyOnWriteArrayList<>()  写时复制将原来的集合复制一份进行写，写完抛弃原来的，读的时候不管（读写分离）
         */


        Vector<String> vector = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                vector.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(vector);
            }, String.valueOf(i)).start();
        }
    }
}
