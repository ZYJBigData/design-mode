package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
/**
 * 浓咖啡
 */

/**
 * @author zhangyingjie
 */
public class Espresso implements Beverage {
    @Override
    public String getDescription() {
        return "浓咖啡";
    }

    @Override
    public double getPrice() {
        return 1.99;
    }
}
