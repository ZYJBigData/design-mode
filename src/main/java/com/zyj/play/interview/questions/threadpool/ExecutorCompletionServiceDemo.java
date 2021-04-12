package com.zyj.play.interview.questions.threadpool;

import java.util.concurrent.*;

/**
 * @author zhangyingjie
 * //验证时间长的线程是否能被阻塞住
 */
public class ExecutorCompletionServiceDemo {
    static class MyThread implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
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
        public Integer call() throws Exception {
            System.out.println("***************come in Callable,sleep 10s");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(50));
        CompletionService<Integer> csMetrics = new ExecutorCompletionService<>(threadPoolExecutor);
        csMetrics.submit(new MyThread1());
        csMetrics.submit(new MyThread());
        for (int i = 0; i < 2; i++) {
            Integer count;
            try {
                count = csMetrics.take().get();
                System.out.println("count ===={}" + count);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
