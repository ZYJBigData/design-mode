package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class Espresso extends Beverage {
    public Espresso(){
        description="espresso";
    }
    @Override
    public double cost() {
        return 1.99;
    }
}
