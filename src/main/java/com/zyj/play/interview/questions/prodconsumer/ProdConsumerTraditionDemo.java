package com.zyj.play.interview.questions.prodconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyingjie
 * 线程通信之生产者 消费者 传统版
 * 一个初始值为0，一个加1一个减一来五轮
 */
public class ProdConsumerTraditionDemo {
    static class ShareData {
        public int number = 0;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increment(){
            lock.lock();
            try {
                //1.判断
                while (number != 0) {
                    condition.await();
                }
                //2.干活
                number++;
                System.out.println(Thread.currentThread().getName() + "\t" + number);
                //3.唤醒所有的线程
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void decrement() {
            lock.lock();
            try {
                //1.判断
                while (number == 0) {
                    condition.await();
                }
                //2.干活
                number--;
                System.out.println(Thread.currentThread().getName() + "\t" + number);
                //3.唤醒所有的线程
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <=5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
