package com.zyj.play.interview.questions.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhangyingjie
 */
public class BlockingQueueDemo3 {
    //阻塞组
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue= new ArrayBlockingQueue<>(3);
        blockingQueue.put("1");
        blockingQueue.put("2");
        blockingQueue.put("3");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
//        blockingQueue.put("4");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }
}
