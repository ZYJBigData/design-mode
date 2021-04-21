package com.zyj.play.interview.questions.thirdquarter.looksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyingjie
 *
 * 1. 同样的道理 await和signal 也必须和lock一起使用
 * 2. 同样的道理 await和signal 也不能调换顺便 也就是先await  后signal
 *
 */
public class LockAwaitSignalDemo {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"...come in");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName()+"....被唤醒");

        },"AA").start();


        new Thread(()->{
          lock.lock();
          try{
              System.out.println(Thread.currentThread().getName()+"....通知");
              condition.signal();
          }finally {
             lock.unlock();
          }
        },"BB").start();
    }
}
