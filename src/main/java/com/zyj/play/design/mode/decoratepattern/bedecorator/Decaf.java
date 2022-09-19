package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
/**
 * 咖啡
 */

/**
 * @author zhangyingjie
 */
public class Decaf implements Beverage {
    @Override
    public String getDescription() {
        return "脱因咖啡";
    }

    @Override
    public double getPrice() {
        return 1.05;
    }
}
