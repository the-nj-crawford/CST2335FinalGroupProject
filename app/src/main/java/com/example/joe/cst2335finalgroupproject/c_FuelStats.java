package com.example.joe.cst2335finalgroupproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Nathan on 2017-12-12.
 */

// Passing an ArrayLis of custom objects to another activity
// https://stackoverflow.com/questions/6681217/help-passing-an-arraylist-of-objects-to-a-new-activity
    // http://www.parcelabler.com/
public class c_FuelStats implements Parcelable {

    private String month;
    private double avg;

    public c_FuelStats(String month, double avg) {
        this.month = month;
        this.avg = avg;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.month);
        dest.writeDouble(this.avg);
    }

    protected c_FuelStats(Parcel in) {
        this.month = in.readString();
        this.avg = in.readDouble();
    }

    public static final Parcelable.Creator<c_FuelStats> CREATOR = new Parcelable.Creator<c_FuelStats>() {
        @Override
        public c_FuelStats createFromParcel(Parcel source) {
            return new c_FuelStats(source);
        }

        @Override
        public c_FuelStats[] newArray(int size) {
            return new c_FuelStats[size];
        }
    };
}