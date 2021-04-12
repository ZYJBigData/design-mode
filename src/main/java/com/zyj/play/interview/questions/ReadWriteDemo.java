package com.zyj.play.interview.questions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangyingjie
 * 读-读能共存
 * 读-写不能共存
 * 写-写不能共存
 */
public class ReadWriteDemo {
    public static void main(String[] args) {
        myCache myCache = new myCache();
        for (int i = 0; i < 5; i++) {
            final int tempI = i;
            new Thread(() -> myCache.put(tempI + "", tempI + ""), String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempI = i;
            new Thread(() -> myCache.get(tempI + ""), String.valueOf(i)).start();
        }
    }
}

class myCache {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
            TimeUnit.MICROSECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取。");
            TimeUnit.MICROSECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成" + map.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }

    }
}

