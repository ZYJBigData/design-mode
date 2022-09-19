package com.zyj.play.design.mode.decoratepattern;
/**
 * 饮料 被装饰的对象
 */

/**
 * @author zhangyingjie
 */
public interface  Beverage {

    public String getDescription();

    /**
     * 每次饮料都可以计算价格
     * @return 返回每种饮料的价格
     */
    public double getPrice();
}
