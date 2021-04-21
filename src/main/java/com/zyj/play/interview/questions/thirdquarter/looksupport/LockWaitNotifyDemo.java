package com.zyj.play.interview.questions.thirdquarter.looksupport;


/**
 * @author zhangyingjie
 * 1. 在wait和notify两个方法中，不能不和synchronized一块使用，运行会报错
 * 2. wait 和notify之间的使用的顺序不能颠倒 也就是不能先唤醒(notify) 再(wait)
 */
public class LockWaitNotifyDemo {
    public static  Object objectLock= new Object();
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName()+"\t --------come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName()+"\t 被通知。");
        },"A").start();

        new Thread(()->{
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t ----通知");
            }

        },"B").start();
    }
}
