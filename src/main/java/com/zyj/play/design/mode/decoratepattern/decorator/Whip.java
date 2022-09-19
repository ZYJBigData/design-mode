package com.zyj.play.design.mode.decoratepattern.decorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
import com.zyj.play.design.mode.decoratepattern.CondimentDecorator;

/**
 * @author zhangyingjie
 */
public class Whip extends CondimentDecorator {
    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",加了糖";
    }

    @Override
    public double getPrice() {
        return 0.20 + beverage.getPrice();
    }
}
