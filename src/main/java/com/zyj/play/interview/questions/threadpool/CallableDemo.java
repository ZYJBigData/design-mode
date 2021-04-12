package com.zyj.play.interview.questions.threadpool;

import java.util.concurrent.*;

/**
 * @author zhangyingjie
 */
public class CallableDemo {
    static class MyThread implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println("***************come in Callable,sleep 2s");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }
    }

    static class MyThread1 implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println("*************** come in Callable sleep 10s");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(50));
        FutureTask<Integer> ft = new FutureTask<>(new MyThread());
        FutureTask<Integer> ft1 = new FutureTask<>(new MyThread1());
//        new Thread(ft1, "BBB").start();
//        new Thread(ft, "AAA").start();
//        System.out.println(ft1.get());
//        System.out.println(ft.get());

        threadPoolExecutor.execute(ft1);
        threadPoolExecutor.execute(ft);

        System.out.println(ft1.get());
        System.out.println(ft.get());
    }
}
