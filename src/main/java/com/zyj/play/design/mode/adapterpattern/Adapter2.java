package com.zyj.play.design.mode.adapterpattern;

public class Adapter2 implements Target {
    private Adaptee adaptee;

    public Adapter2(Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void getReceptacle1() {
        adaptee.getReceptacle1();
    }

    @Override
    public void getReceptacle2() {
        System.out.println("扩展插口，此为三孔插口！");
    }
}
