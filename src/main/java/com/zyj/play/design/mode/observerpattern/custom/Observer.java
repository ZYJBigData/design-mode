package com.zyj.play.design.mode.observerpattern.custom;

/**
 * @author zhangyingjie
 * 观察者
 */
public interface Observer {
    /**
     * 当气象观测值改变时，主题会把这些状态值当做方法的参数，传递给观察者
     * @param temperature 温度
     * @param humidity  湿度
     * @param pressure 压力
     */
    public void update(float temperature,float humidity,float pressure);
}
