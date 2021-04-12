package com.zyj.play.interview.questions.prodconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyingjie
 * 一.synchronized和lock之间的区别
 * 1.原始构成：
 * 1. synchronized是关键字 是jvm层面 是java的关键字
 * monitorenter(底层是通过monitor对象来完成，其实wait/notify等方法也依赖于moniter对象，只有在同步代码块或者方法中才能调用这些方法)
 * monitorexit
 * 2. lock api层 java5后新发生的一个类。
 * 2.使用方法
 * 1.synchronized 不需要手动释放，当synchronized代码执行完毕之后系统会自动让线程释放对锁的占用
 * 2.Reentranlock 需要手动释放，有可能会导致死锁的问题
 * 3. 等待是否可中短
 * 1.syncronized 不可中断，除非异常抛出，或者正常运行完成
 * 2.Reentranlock 可以中断, 设置超时方法 trylock(long timeout,TimeUnit unit)
 * 4. 加锁是否公平
 * 1.synchronized非公平锁
 * 2.Reentranlock 默认是非公平锁，构造方法可以传入boolean的值,true为公平锁，false为非公平锁
 * 5.绑定多个条件Condition
 * 1.synchronized 没有
 * 2.Reentranlock 用来分组唤醒需要唤醒的线程们，可以准确的唤醒，而不像synchronized随机唤醒一个线程，或者全部唤醒
 * <p>
 * 题目：多线程之间顺序调用，实现A->B->C三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着AA打印5次，BB打印10次，CC打印15次
 * ----
 * 来10轮
 */
public class SynAndReentrantLockDemo {
    static class ShareResource {
        private int number = 1;
        private Lock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();

        private void print5() {
            lock.lock();
            try {
                //1.判断
                while (number != 1) {
                    c1.await();
                }
                //2.干活
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t 干活");
                }
                //3.通知;
                number = 2;
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private void print10() {
            lock.lock();
            try {
                //1.判断
                while (number != 2) {
                    c1.await();
                }
                //2.干活
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t 干活");
                }
                //3.通知;
                number = 3;
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private void print15() {
            lock.lock();
            try {
                //1.判断
                while (number != 3) {
                    c3.wait();
                }
                //2.干活
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t 干活");
                }
                //3.通知
                number = 4;
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "CC").start();
    }
}
