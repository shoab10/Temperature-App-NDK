package com.example.shoab.myproject.model;

/**
 * Created by Shoab on 3/1/2016.
 */
public class Forecast {
    private String day;
    private float temp;

    public Forecast(String day, float temp) {
        this.day = day;
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
