package com.zyj.play.design.mode.strategypattern.behavior;

public class FlyNoWay implements FlyBehavior {
    public void fly() {
        System.out.println("我不能飞");
    }
}
