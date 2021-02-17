package com.zyj.play.design.mode.decoratepattern;

/**
 * @author zhangyingjie
 */
public abstract class Beverage {
    public String description="Unknown Beverage";

    public String getDescription() {
        return description;
    }

    /**
     * 每次饮料都可以计算价格
     * @return 返回每种饮料的价格
     */
    public abstract double cost();
}
