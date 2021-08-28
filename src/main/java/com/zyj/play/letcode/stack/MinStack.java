package com.zyj.play.letcode.stack;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 *
 * @author zhangyingjie
 */
public class MinStack {

    LinkedList<Integer> a;

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("min=={}" + minStack.min());
        minStack.pop();
        System.out.println("top=={}" + minStack.top());
        System.out.println("min=={}" + minStack.min());
    }

    public MinStack() {
        a = new LinkedList<>();
    }

    public void push(int x) {
        a.addFirst(x);
    }

    public void pop() {
        a.removeFirst();
    }

    public int top() {
        return a.getFirst();
    }

    public int min() {
        return a.stream().min(Comparator.comparingInt(o -> o)).get();
    }
}
