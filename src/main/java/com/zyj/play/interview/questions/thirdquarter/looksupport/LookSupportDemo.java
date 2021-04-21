package com.zyj.play.interview.questions.thirdquarter.looksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangyingjie
 */
public class LookSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3L);
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + " .... come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "被唤醒");
        }, "a");
        a.start();

        Thread b = new Thread(() -> {
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "\t" + "...通知了");
        }, "b");

        b.start();
    }
}
