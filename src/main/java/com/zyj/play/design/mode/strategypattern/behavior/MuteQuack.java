package com.zyj.play.design.mode.strategypattern.behavior;

public class MuteQuack implements QuackBehavior {
    public void Quack() {
        System.out.println("无声音");
    }
}
