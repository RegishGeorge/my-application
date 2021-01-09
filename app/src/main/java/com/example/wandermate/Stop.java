package com.example.wandermate;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stop {
    @PrimaryKey
    private int stop_id;

    private String stop_name;
    private double stop_latitude;
    private double stop_longitude;

    public Stop(int stop_id, String stop_name, double stop_latitude, double stop_longitude) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_latitude = stop_latitude;
        this.stop_longitude = stop_longitude;
    }

    public void setStop_id(int stop_id) {
        this.stop_id = stop_id;
    }

    public int getStop_id() {
        return stop_id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public double getStop_latitude() {
        return stop_latitude;
    }

    public double getStop_longitude() {
        return stop_longitude;
    }
}
