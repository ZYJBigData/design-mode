package com.zyj.play.interview.questions.aop.jdk;

/**
 * 需要动态代理的接口
 * @author zhangyingjie
 */
public interface Subject {

    /**
     * 你好
     *
     * @param name
     * @return
     */
    public String SayHello(String name);

    /**
     * 再见
     *
     * @return
     */
    public String SayGoodBye();

}
