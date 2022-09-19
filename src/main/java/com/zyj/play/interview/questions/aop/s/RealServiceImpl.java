package com.zyj.play.interview.questions.aop.s;

public class RealServiceImpl implements Service{
    @Override
    public void operate() {
        System.out.println("执行方法...");
    }
}
