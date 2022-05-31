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
            //模拟100个线程抢锁
            for (int i = 0; i < 5; i++) {
                new Thread(new TestThread(i, lock, zkClient)).start();
            }
            Thread.sleep(10000);
            for (int i = 0; i < 5; i++) {
                new Thread(new TestThread(i, lock, zkClient)).start();
            }
        } catch (Exception e) {
            log.error("eeeeee={}", e.getMessage());
        }
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
                Thread.sleep(10000);
                //等到1秒后释放锁
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
