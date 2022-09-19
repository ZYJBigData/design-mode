package com.zyj.play.design.mode.factorypattern;


/**
 * 设置具体的工厂，生产intel所有的产品
 */
public class IntelFactory implements AbstractFactory {


    @Override
    public Cpu createCpu() {
        return new IntelCpu(555);
    }

    @Override
    public MainBoard creatMainBoard() {
        return new IntelMainBoard(555);
    }
}
