package com.zyj.play.letcode.queue;

import java.util.LinkedList;

/**
 * @author zhangyingjie
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 * 示例 1：
 * <p>
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 * <p>
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 * @author zhangyingjie
 */
public class CQueue {
    //用Linklist来模拟两个栈 ,Linklist底层是用数组实现的
    LinkedList<Integer> a, b;

    public CQueue() {
        a = new LinkedList<>();
        b = new LinkedList<>();
    }

    public static void main(String[] args) {
        CQueue cQueue = new CQueue();

    }

    public void appendTail(int value) {
        a.add(value);
    }

    public int deleteHead() {
        if(!b.isEmpty()) {
            return b.removeLast();
        }
        if(a.isEmpty()) {
            return -1;
        }
        while(!a.isEmpty()) {
            b.addLast(a.removeLast());
        }
        return b.removeLast();
    }
}
