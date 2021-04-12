package com.zyj.play.interview.questions.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhangyingjie
 *
 *
 */
public class BlockingQueueDemo2 {
    //返回布尔组
    public static void main(String[] args) {
        /**
         * true
         * true
         * true
         * false
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("1"));
        System.out.println(blockingQueue.offer("2"));
        System.out.println(blockingQueue.offer("3"));
        System.out.println(blockingQueue.offer("4"));
        /**
         * 1
         * 2
         * 3
         * null
         */
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }
}
