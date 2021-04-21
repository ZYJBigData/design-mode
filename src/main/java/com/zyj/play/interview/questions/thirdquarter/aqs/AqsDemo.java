package com.zyj.play.interview.questions.thirdquarter.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyingjie
 * 带入一个银行办理业务的案例模拟银行是如何进行线程的管理和唤醒机制
 * 3个线程模拟三个来银行网点受理窗口办理业务的顾客
 */
public class AqsDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        //A顾客就是第一个客户，此时受理窗口没有一个人，A可以直接办理
        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ".... come in");
            try {
                TimeUnit.MINUTES.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        //B顾客 第二个客户，由于受理窗口只有一个(只能一个窗口持有锁)此时B只能等待
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "..... come in");
            } finally {
                lock.unlock();
            }
        }, "B").start();

        //顾客 第三个客户，由于受理业务的窗口只有一个(只能一个线程持有锁)，此时C只能等待，进入候客区
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "..... come in");
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
