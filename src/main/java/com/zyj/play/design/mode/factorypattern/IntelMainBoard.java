package com.zyj.play.design.mode.factorypattern;

public class IntelMainBoard implements MainBoard {
    private int cpuHoles;//表示主板上的CPU插槽孔数，对应脚针数

    public IntelMainBoard(int cpuHoles) {
        this.cpuHoles = cpuHoles;
    }

    @Override
    public void installCpu() {
        System.out.println("Intel主板上的CPU插槽孔数：" + cpuHoles);
    }
}
