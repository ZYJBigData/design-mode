package com.zyj.play.design.mode.observerpattern.javaembedded;

import java.util.Observable;
/**
 * 被观察对象
 */

/**
 * @author zhangyingjie
 */
public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    /**
     * 注意这里没有调用notifyObservers()表示的用的是拉的方式
     * 没有调用notifyObservers(Object args)
     */
    public void measurementsChanged(){
        setChanged();
        notifyObservers();
    }
    public void setMeasurements(float temperature,float humidity,float pressure){
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        measurementsChanged();
    }

    public float getTemperature(){
        return temperature;
    }

    public float getHumidity(){
        return humidity;
    }

    public float getPressure(){
        return pressure;
    }

}
