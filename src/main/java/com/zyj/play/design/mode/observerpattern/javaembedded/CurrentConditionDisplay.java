package com.zyj.play.design.mode.observerpattern.javaembedded;

import com.zyj.play.design.mode.observerpattern.custom.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * @author zhangyingjie
 */
public class CurrentConditionDisplay implements Observer , DisplayElement {
    private float temperature;
    private float humidity;

    public CurrentConditionDisplay (Observable observable){
        observable.addObserver(this);
    }
    @Override
    public void display() {
        System.out.println("Current condition: " + temperature + "F degress and "
                + humidity + "% humidity");
    }

    @Override
    public void update(Observable obs, Object arg) {
        if(obs instanceof WeatherData){
            WeatherData weatherData=(WeatherData) obs;
            temperature=weatherData.getTemperature();
            humidity=weatherData.getHumidity();
            display();
        }
    }
}
