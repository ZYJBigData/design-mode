package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "house blend coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
