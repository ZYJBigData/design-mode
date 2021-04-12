package com.zyj.play.interview.questions.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 */
public class BlockingQueueDemo4 {
    //超时退出
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("1", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("2", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("3", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("4", 3, TimeUnit.SECONDS));


        System.out.println(blockingQueue.poll( 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll( 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll( 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll( 3, TimeUnit.SECONDS));
    }
}
