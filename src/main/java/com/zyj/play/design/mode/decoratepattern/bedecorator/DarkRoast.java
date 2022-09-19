package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class DarkRoast implements Beverage {
    @Override
    public String getDescription() {
        return "焦抄咖啡";
    }

    @Override
    public double getPrice() {
        return 0.99;
    }
}
