package com.zyj.play.design.mode.decoratepattern.decorator;

import com.zyj.play.design.mode.decoratepattern.Beverage;
import com.zyj.play.design.mode.decoratepattern.CondimentDecorator;

/**
 * @author zhangyingjie
 */
public class Soy extends CondimentDecorator {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",加了大豆";
    }

    @Override
    public double getPrice() {
        return 0.10 + beverage.getPrice();
    }
}
