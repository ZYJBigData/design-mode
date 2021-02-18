package com.zyj.play.design.mode.factorypattern;

import com.zyj.play.design.mode.factorypattern.pizza.Pizza;
import com.zyj.play.design.mode.factorypattern.store.ChicagoStylePizzaStore;
import com.zyj.play.design.mode.factorypattern.store.NYStylePizzaStore;

/**
 * @author zhangyingjie
 */
public class OrderPizza {
    public static void main(String[] args) {
        ChicagoStylePizzaStore css = new ChicagoStylePizzaStore();
        NYStylePizzaStore nys = new NYStylePizzaStore();

        Pizza pizza = css.orderPizza("cheese");
        System.out.println(pizza);
        System.out.println();
        Pizza pizza1 = nys.orderPizza("cheese");
        System.out.println(pizza1);


    }

}
