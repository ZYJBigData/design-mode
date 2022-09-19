package com.zyj.play.design.mode.factorypattern;

/**
 * 工厂接口,不制造具体的产品
 */
public interface AbstractFactory{
    /**
     * 创建Cpu对象
     * @return Cpu对象
     */
    public Cpu createCpu();
    /**
     * 创建MainBoard对象
     * @return MainBoard对象
     */
    public MainBoard creatMainBoard();

}
