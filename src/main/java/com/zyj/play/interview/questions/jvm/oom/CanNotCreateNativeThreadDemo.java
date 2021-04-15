package com.zyj.play.interview.questions.jvm.oom;

/**
 * @author zhangyingjie
 * 在Linux中非root用户一个进程可以生成1024个线程
 */
public class CanNotCreateNativeThreadDemo {
    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            System.out.println("***************************i:" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }
}
