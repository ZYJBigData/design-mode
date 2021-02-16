package com.zyj.play.design.mode.observerpattern.javaembedded;

/**
 * @author zhangyingjie
 */
public class WeatherStation {
    public static void main(String[] args) {
        while (true) {
            WeatherData weatherData = new WeatherData();
            CurrentConditionDisplay ccd = new CurrentConditionDisplay(weatherData);
            ForecastDisplay fd = new ForecastDisplay(weatherData);
            weatherData.setMeasurements(80, 65, 30.4f);
        }
    }
}
