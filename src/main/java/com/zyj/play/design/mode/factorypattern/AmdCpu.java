package com.zyj.play.design.mode.factorypattern;

import java.lang.annotation.Annotation;

public class AmdCpu implements Cpu {
    private int pins;

    public AmdCpu(int pins) {
        this.pins = pins;
    }

    @Override
    public void calculate() {
        System.out.println("CMD CPU的脚针数目：" + pins);
    }
    
    
    public class aa implements  Override{

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }
}
