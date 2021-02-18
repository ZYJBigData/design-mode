package com.zyj.play.design.mode.factorypattern.store;

import com.zyj.play.design.mode.factorypattern.pizza.*;

/**
 * @author zhangyingjie
 */
public class ChicagoStylePizzaStore extends PizzaStore {
    public ChicagoStylePizzaStore(){

    }
    @Override
    Pizza createPizza(String type) {
        Pizza pizza = null;
        if ("cheese".equalsIgnoreCase(type)) {
            pizza = new ChicagoStyleCheesePizza();
        } else if ("pepperoni".equalsIgnoreCase(type)) {
            pizza = new ChicagoStylePepperoniPizza();
        } else if ("clam".equalsIgnoreCase(type)) {
            pizza = new ChicagoStyleClamPizza();
        } else if ("veegie".equalsIgnoreCase(type)) {
            pizza = new ChicagoStyleVeggiePizza();
        }
        return pizza;
    }
}
