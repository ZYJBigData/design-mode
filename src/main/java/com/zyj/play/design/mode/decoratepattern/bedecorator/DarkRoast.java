package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "dark roast";
    }

    @Override
    public double cost() {
        return 0.99;
    }
}
