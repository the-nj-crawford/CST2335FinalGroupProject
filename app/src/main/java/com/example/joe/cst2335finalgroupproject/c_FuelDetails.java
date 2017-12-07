package com.example.joe.cst2335finalgroupproject;

import java.util.Date;

/**
 * Created by Nathan on 2017-12-01.
 */

public class c_FuelDetails {

    private double price;
    private double litres;
    private double kilometers;

    private Date date;

    public c_FuelDetails(double price, double litres, double kilometers, Date date) {
        this.price = price;
        this.litres = litres;
        this.kilometers = kilometers;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLitres() {
        return litres;
    }

    public void setLitres(double litres) {
        this.litres = litres;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}