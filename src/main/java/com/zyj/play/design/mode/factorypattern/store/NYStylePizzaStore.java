package com.zyj.play.design.mode.factorypattern.store;


import com.zyj.play.design.mode.factorypattern.pizza.NYStyleCheesePizza;
import com.zyj.play.design.mode.factorypattern.pizza.NYStylePepperoniPizza;
import com.zyj.play.design.mode.factorypattern.pizza.Pizza;

/**
 * @author zhangyingjie
 */
public class NYStylePizzaStore extends PizzaStore {
    @Override
    Pizza createPizza(String type) {
        Pizza pizza = null;
        if ("cheese".equalsIgnoreCase(type)) {
            pizza = new NYStyleCheesePizza();
        } else if ("pepperoni".equalsIgnoreCase(type)) {
            pizza = new NYStylePepperoniPizza();
        } else if ("clam".equalsIgnoreCase(type)) {
            pizza = new NYStylePepperoniPizza();
        } else if ("veegie".equalsIgnoreCase(type)) {
            pizza = new NYStylePepperoniPizza();
        }
        return pizza;
    }
}
