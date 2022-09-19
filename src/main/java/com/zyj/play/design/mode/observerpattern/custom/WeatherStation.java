package com.zyj.play.design.mode.observerpattern.custom;

/**
 * 气象观察站
 */

/**
 * @author zhangyingjie
 */
public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionDisplay ccd = new CurrentConditionDisplay(weatherData,"观察1");
        CurrentConditionDisplay ccd1 = new CurrentConditionDisplay(weatherData,"观察2");
        CurrentConditionDisplay ccd2 = new CurrentConditionDisplay(weatherData,"观察3");
        

        weatherData.setMeasurements(80, 65, 30.4f);
    }
}
