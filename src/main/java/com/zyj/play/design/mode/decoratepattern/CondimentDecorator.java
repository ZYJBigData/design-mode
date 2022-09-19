package com.zyj.play.design.mode.decoratepattern;

/**
 * 装饰类
 */

/**
 * @author zhangyingjie
 */
public class CondimentDecorator implements Beverage {
    @Override
    public String getDescription() {
        return "我只是装饰器，不知道具体的描述";

    }

    @Override
    public double getPrice() {
        return 0;
    }
}
