package com.zyj.play.interview.questions.prodconsumer;

import com.sun.tools.javac.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangyingjie
 */
public class ProdConsumerBlockQueueDemo {
    static class MyResource {
        //默认开启，进行生产+消费
        private volatile boolean FLAG = true;
        private AtomicInteger atomicInteger = new AtomicInteger();
        //这是思想 可以传入不同的队列
        BlockingQueue<String> blockingQueue;

        public MyResource(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        public void myPro() throws InterruptedException {
            String data;
            boolean retValue;
            while (FLAG) {
                data = atomicInteger.incrementAndGet() + "";
                retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
                if (retValue) {
                    System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println(Thread.currentThread().getName() + "\t 大老板叫停了,表示FLAG=false ,生产座动作结束");
        }

        public void myConsumer() throws InterruptedException {
            String data;
            while (FLAG) {
                data = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (data == null || "".equalsIgnoreCase(data)) {
                    FLAG = false;
                    System.out.println(Thread.currentThread().getName() + "\t 消费队列失败");
                    System.out.println();
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t 消费队列" + data + "成功");
            }
        }

        public void stop() {
            this.FLAG = false;
        }
    }

    public static void main(String[] args) {
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        MyResource myResource = new MyResource(arrayBlockingQueue);
        new Thread(() -> {
            try {
                myResource.myPro();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Pro").start();

        new Thread(() -> {
            try {
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");

        myResource.stop();
    }
}
