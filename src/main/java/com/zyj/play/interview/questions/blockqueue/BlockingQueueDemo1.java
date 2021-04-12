package com.zyj.play.interview.questions.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhangyingjie
 * 阻塞队列：
 *   当队列为空的时候，获取队列中元素的操作将被阻塞
 *   当队列为满的时候，插入队列中元素的操作将被阻塞
 * 为什么要用阻塞队列：
 *   在多线程领域，所谓阻塞，在一些情况下会唤醒线程，一旦情况满足被挂起的线程又会自动被唤起
 *   我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockQueue都给你包办了
 *   在juc中发布之前，在多线程的情况下，我们每个程序员都必须去控制一些细节，尤其还要考虑效率和线程安全，这会给我们的程序带来不小的复杂度
 * 例子：
 * ArrayBlockingQueue:由数组组成的有界阻塞队列
 * LinkedBlockingQueue:由链表结构组成的有界阻塞队列（但是大小默认值是INTEGER.MAX_VALUE）
 * PriorityBlockingQueue:支持优先级排序的无线阻塞队列
 * DelayQueue:使用优先级队列实现延迟无界阻塞队列
 * SynchronousQueue: 不存元素的阻塞队列，即是单个元素队列,队列中只存在一个元素。
 * LinkedTransferQueue: 由链表结构组成的无界组成队列
 * LinkedBlockingDeque:由链表组成的双向组成队列
 */
public class BlockingQueueDemo1 {
    //抛出异常组
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //添加元素过多抛出异常  Exception in thread "main" java.lang.IllegalStateException: Queue full
        System.out.println(blockingQueue.add("1"));
        System.out.println(blockingQueue.add("2"));
        System.out.println(blockingQueue.add("3"));
        //检查队列是否为空，队列的首位元素是什么
        System.out.println(blockingQueue.element());
//        Exception in thread "main" java.util.NoSuchElementException
        for (int i = 0; i < 3; i++) {
            System.out.println(blockingQueue.remove());
        }
    }
}
