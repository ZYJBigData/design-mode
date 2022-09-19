package com.zyj.play.design.mode.factorypattern;

public class AmdCpu implements Cpu {
    private int pins;

    public AmdCpu(int pins) {
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("CMD CPU的脚针数目：" + pins);
    }
}
