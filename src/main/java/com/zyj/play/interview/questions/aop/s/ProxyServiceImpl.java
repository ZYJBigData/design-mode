package com.zyj.play.interview.questions.aop.s;

public class ProxyServiceImpl implements Service{
    private Service service;

    public ProxyServiceImpl(Service service) {
        this.service = service;
    }
    @Override
    public void operate() {
        long startTime = System.currentTimeMillis();
        service.operate();
        System.out.println("执行耗时:" + (System.currentTimeMillis() - startTime));
    }
}
