package com.zyj.play.interview.questions.threadpool;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 * 造成死锁的原因：
 * 1.死锁是指两个或者两个以上的进程在执行过程中因争夺资源而造成的一种互相等待的现象，若无外力他们将无法推进下去。
 * 若系统资源充足，进程的资源都能得到满足，死锁的可能性就很低，否则会因为争夺有限的资源而陷入死锁。
 */
public class DeadLockDemo {
    static class HoldLockThread implements Runnable {
        private String lockA;
        private String lockB;

        public HoldLockThread(String lockA, String lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有0："
                        + lockA + "\t 尝试获得：" + lockB);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t 自己持有1："
                            + lockB + "\t 尝试获得：" + lockB);
                }
            }
        }
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();
    }
}
