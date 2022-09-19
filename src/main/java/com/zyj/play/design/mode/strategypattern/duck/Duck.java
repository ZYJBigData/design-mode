package com.zyj.play.design.mode.strategypattern.duck;

import com.zyj.play.design.mode.strategypattern.behavior.FlyBehavior;
import com.zyj.play.design.mode.strategypattern.behavior.QuackBehavior;

public abstract class Duck {
    /**
     * 飞的策略
     */
    public FlyBehavior flyBehavior;
    /**
     * 叫的策略
     */
    public QuackBehavior quackBehavior;

    public void performFly(){
        flyBehavior.fly();;
    }
    public void performQuack(){
        quackBehavior.Quack();
    }

    public abstract void display();

    public void swim(){
        System.out.println("所有的鸭子都能浮起来，即使是诱饵");
    }
}
