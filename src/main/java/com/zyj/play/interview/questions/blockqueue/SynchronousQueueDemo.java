package com.zyj.play.interview.questions.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put "+1);
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"\t put "+2);
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put "+3);
                blockingQueue.put("3");
            }catch (Exception e){
                e.printStackTrace();
            }

        },"AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t get "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t get "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t get "+blockingQueue.take());
            }catch (Exception e){
                e.printStackTrace();
            }

        },"BBB").start();
    }
}
