package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class HouseBlend implements Beverage {

    @Override
    public String getDescription() {
        return "首选咖啡";
    }

    @Override
    public double getPrice() {
        return 0.89;
    }
}
