package com.zyj.play.interview.questions.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 * <p>
 * 1.为什么要用线程池
 * 线程池的主要作用是控制线程的数量，处理过程中将任务放入到队列，然后在线程创建后启动这些任务，
 * 如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行
 * 2.主要特点就是：
 * 线程复用，控制最大并发数，管理线程
 * 第一：降低资源消耗，将重复利用已创建的线程创建和销毁造成的消耗
 * 第二：提高响应速度，当任务到达时，任务不需要等到线程创建就会执行
 * 第三：提高线程的可管理性，线程是稀缺的资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程可以进行统一分配，调优和监控。
 * 3.创建线程池的三种方式：
 * Executors.newFixedThreadPool(5);   执行长期任务，性能好很多
 * Executors.newSingleThreadExecutor(); 一个任务一个任务执行
 * Executors.newCachedThreadPool();  执行短期异步的小程序或者负载较轻的服务
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        //一池五个线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        ExecutorService threadPool = Executors.newCachedThreadPool();
        //模拟10个用户来办理业务，每个用户就是来自外部的线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
