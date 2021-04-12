package com.zyj.play.design.mode.factorypattern.ingredient;

/**
 * @author zhangyingjie
 */
public interface PizzaIngredientFactory {
    /**
     * 制作面团
     *
     * @return Dough
     */
    Dough createDough();

    /**
     * 制作调味料
     *
     * @return Sauce
     */
    Sauce createSauce();

    /**
     * 制作芝士
     *
     * @return Cheese
     */
    Cheese creteCheese();

    /**
     * 制作蔬菜
     *
     * @return Veggies
     */
    Veggies[] createVeggies();

    /**
     * 制作意大利香肠
     *
     * @return pepperoni
     */
    Pepperoni createPepperoni();

    /**
     * 制作蛤蚌
     *
     * @return Clams
     */
    Clams createClams();
}
