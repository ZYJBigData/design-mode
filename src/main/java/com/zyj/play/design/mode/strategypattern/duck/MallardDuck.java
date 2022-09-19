package com.zyj.play.design.mode.strategypattern.duck;

import com.zyj.play.design.mode.strategypattern.behavior.FlyNoWay;
import com.zyj.play.design.mode.strategypattern.behavior.MuteQuack;

public class MallardDuck extends Duck {
    public MallardDuck() {
//        quackBehavior = new Quack();
//        flyBehavior = new FlyWithWings();
        quackBehavior = new MuteQuack();
        flyBehavior = new FlyNoWay();

    }

    @Override
    public void display() {
        System.out.println("mallar鸭子能游泳");
    }
}
