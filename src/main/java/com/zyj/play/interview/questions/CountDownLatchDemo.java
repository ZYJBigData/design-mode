package com.zyj.play.interview.questions;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangyingjie
 * 等于所有的线程为0的时候，再执行主线程。
 * 枚举用简单的mysql数据库来看待
 * 例子 是等六个同学都离开教室 班长才能离开
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t上完自习 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门走人");
    }
}
