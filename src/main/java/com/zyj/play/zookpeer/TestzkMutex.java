package com.zyj.play.zookpeer;

import com.zyj.play.zookpeer.common.ZookpeerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

@Slf4j
public class TestzkMutex {

    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = ZookpeerClient.getClient();
        zkClient.start();
        try {
            final InterProcessMutex lock = new InterProcessMutex(zkClient, "/master");
//            //模拟100个线程抢锁
//            for (int i = 0; i < 1; i++) {
//                new Thread(new TestThread(i, lock, zkClient)).start();
//            }
            lock.acquire();
            System.out.println("第一层锁");
            Thread.sleep(100);
            processMutex(zkClient);
            lock.release();
            System.out.println("第一层锁释放");
        } catch (Exception e) {
            log.error("eeeeee={}", e.getMessage());
        }
    }

    public static void processMutex(CuratorFramework zkClient) throws Exception {
        final InterProcessMutex lock = new InterProcessMutex(zkClient, "/master/c1");
        System.out.println("可重入锁");
        lock.acquire();
        Thread.sleep(100);
        lock.release();
        System.out.println("可重入锁释放");
    }

    static class TestThread implements Runnable {
        private Integer threadFlag;
        private InterProcessMutex lock;
        private CuratorFramework zkClient;

        public TestThread(Integer threadFlag, InterProcessMutex lock, CuratorFramework zkClient) {
            this.threadFlag = threadFlag;
            this.lock = lock;
            this.zkClient = zkClient;
        }

        @Override
        public void run() {
            try {
                lock.acquire();
                System.out.println("第" + threadFlag + "线程获取到了锁");
//                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/master/" + threadFlag, "".getBytes(StandardCharsets.UTF_8));
                Thread.sleep(6000);
                //等到1秒后释放锁
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                    System.out.println("我的锁释放了~~~");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
