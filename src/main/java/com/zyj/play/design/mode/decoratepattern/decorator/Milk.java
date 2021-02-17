package com.zyj.play.design.mode.decoratepattern.decorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
import com.zyj.play.design.mode.decoratepattern.CondimentDecorator;

/**
 * @author zhangyingjie
 */
public class Milk extends CondimentDecorator {
    Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",milk";
    }

    @Override
    public double cost() {
        return 0.10 + beverage.cost();
    }
}
