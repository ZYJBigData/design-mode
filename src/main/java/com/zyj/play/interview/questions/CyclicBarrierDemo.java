package com.zyj.play.interview.questions;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhangyingjie
 * cyclicBarrier 是集齐多少可以干什么 是加法 和CountDownLatch 相反
 *  集齐七个龙珠可以召唤神龙
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{ System.out.println("**************召唤神龙");});
        for (int i = 0; i < 7; i++) {
            final int tempI= i;
            new Thread(()->{
                System.out.println("收集到第 "+tempI +"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
