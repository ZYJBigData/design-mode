package com.zyj.play.design.mode.decoratepattern.bedecorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;

/**
 * @author zhangyingjie
 */
public class Decaf extends Beverage {
    public Decaf(){
        description="decaf";
    }
    @Override
    public double cost() {
        return 1.05;
    }
}
