package com.zyj.play.design.mode.strategypattern.duck;

import com.zyj.play.design.mode.strategypattern.behavior.FlyWithWings;
import com.zyj.play.design.mode.strategypattern.behavior.Quack;

public class MallardDuck extends Duck {
    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
    @Override
    public void display() {
        System.out.println("mallar duck can swim");
    }
}
