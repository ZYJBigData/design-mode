package com.zyj.play.design.mode.factorypattern.store;

import com.zyj.play.design.mode.factorypattern.pizza.Pizza;

/**
 * @author zhangyingjie
 */
public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.box();
        pizza.cut();
        return pizza;
    }

    /**
     * 制作pizza 每个商店各自实现，每个人实现的方式不同
     *
     * @param type 要制作的pizza的类型
     * @return 返回制作的pizza
     */
    abstract Pizza createPizza(String type);
}
