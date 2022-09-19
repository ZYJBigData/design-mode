package com.zyj.play.design.mode.factorypattern;

/**
 * amd 工厂设计，设计具体amd工厂，生产所有的产品
 */
public class AmdFactory implements AbstractFactory {

    @Override
    public Cpu createCpu() {
        return new AmdCpu(666);
    }

    @Override
    public MainBoard creatMainBoard() {
        return new AmdMainBoard(666);
    }
}
