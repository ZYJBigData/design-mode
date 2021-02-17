package com.zyj.play.design.mode.decoratepattern.decorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
import com.zyj.play.design.mode.decoratepattern.CondimentDecorator;

/**
 * @author zhangyingjie
 */
public class Mocha extends CondimentDecorator {
    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", mocha";
    }

    @Override
    public double cost() {
        return 0.20 + beverage.cost();
    }
}
