package com.example.joe.cst2335finalgroupproject;

/**
 * Created by Joe on 2017-12-06.
 */

public class t_ThermostatRule {

    String day;
    int hour;
    int minute;
    String meridiem;
    int temp;

    public t_ThermostatRule(String day, int hour, int minute, String meridiem, int temp) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.meridiem = meridiem;
        this.temp = temp;
    }

    @Override
    public String toString() {
        return day + " " + hour + ":" + minute + " " + meridiem + " -> " + temp;
    }
}
