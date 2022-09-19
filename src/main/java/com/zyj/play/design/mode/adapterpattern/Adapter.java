package com.zyj.play.design.mode.adapterpattern;

/**
 * 类的适配器
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void getReceptacle2() {
        System.out.println("扩展插口，此为三孔插口！");
    }
}
