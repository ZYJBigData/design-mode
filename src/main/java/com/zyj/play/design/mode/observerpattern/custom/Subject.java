package com.zyj.play.design.mode.observerpattern.custom;

/**
 * @author zhangyingjie
 * 这个类是个主题
 */
public interface Subject {
    /**
     * 注册观察者
     * @param o 观察者
     */
    public void registerObserver(Observer o);

    /**
     * 删除观察者
     * @param o 观察者
     */
    public void deleteObserver(Observer o);

    /**
     * 当主题状态改变时，这个方法会被调用，以通知所有的观察者
     */
    public void notifyObservers();
}
