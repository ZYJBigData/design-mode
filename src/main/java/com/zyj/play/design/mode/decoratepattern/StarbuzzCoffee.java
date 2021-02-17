package com.zyj.play.design.mode.decoratepattern;

import com.zyj.play.design.mode.decoratepattern.bedecorator.DarkRoast;
import com.zyj.play.design.mode.decoratepattern.bedecorator.Espresso;
import com.zyj.play.design.mode.decoratepattern.decorator.Mocha;
import com.zyj.play.design.mode.decoratepattern.decorator.Whip;

/**
 * @author zhangyingjie
 */
public class StarbuzzCoffee {
    public static void main(String[] args) {
        Beverage bea = new Espresso();
        //做一杯espresso,不需要调料 打印出描述和价格
        System.out.println(bea.getDescription() + " $ " + bea.cost());

        //制作一杯darkRoast对象
        Beverage bea1 = new DarkRoast();
        System.out.println(bea1.getDescription() + " $ " + bea1.cost());
        bea1 = new Mocha(bea1);
        bea1 = new Mocha(bea1);
        bea1 = new Whip(bea1);

        System.out.println(bea1.getDescription() + " $ " + bea1.cost());
    }
}
