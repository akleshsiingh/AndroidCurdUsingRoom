package com.androidfizz.model;

/**
 * Created by Aklesh on 3/9/2018.
 */

public class Address {
    public String state;
    public String city;

    public Address(String state, String city) {
        this.state = state;
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
