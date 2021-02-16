package com.zyj.play.design.mode.observerpattern.javaembedded;

import com.zyj.play.design.mode.observerpattern.custom.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * @author zhangyingjie
 */
public class ForecastDisplay implements Observer, DisplayElement {
    private float currentPressure = 29.92f;
    private float lastPressure;

    public ForecastDisplay(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Forecast now : " + currentPressure + "F degress,last: "
                + lastPressure + "F degress");
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) obs;
            lastPressure = currentPressure;
            currentPressure = weatherData.getPressure();
            display();
        }
    }
}
