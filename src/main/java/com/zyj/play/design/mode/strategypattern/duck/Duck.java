package com.zyj.play.design.mode.strategypattern.duck;

import com.zyj.play.design.mode.strategypattern.behavior.FlyBehavior;
import com.zyj.play.design.mode.strategypattern.behavior.QuackBehavior;

public abstract class Duck {
    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    public void performFly(){
        flyBehavior.fly();;
    }
    public void performQuack(){
        quackBehavior.Quack();
    }

    public abstract void display();

    public void swim(){
        System.out.println("All duck can float,even decoys");
    }
}
