package com.zyj.play.design.mode.adapterpattern;


public class Computer {
    public static void main(String[] args) {
        Target target = new Adapter();
        target.getReceptacle1();
        target.getReceptacle2();

        Adaptee adaptee = new Adaptee();
        Target target2 = new Adapter2(adaptee);
        target2.getReceptacle1();
        target2.getReceptacle2();
    }
}
