package com.zyj.play.design.mode.factorypattern;

public class IntelCpu implements Cpu {
    private int pins;

    public IntelCpu(int pins) {
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("Intel CPU的脚针数目：" + pins);
    }
}
