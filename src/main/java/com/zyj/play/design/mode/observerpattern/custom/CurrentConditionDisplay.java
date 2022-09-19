package com.zyj.play.design.mode.observerpattern.custom;
/**
 * 观察者
 */

/**
 * @author zhangyingjie
 */
public class CurrentConditionDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;
    private String name;
    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData, String name) {
        this.weatherData = weatherData;
        this.name = name;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("观察者名字是：" + name + "    Current condition: " + temperature + "F degress and "
                + humidity + "% humidity" + "pressure: " + pressure);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
}
