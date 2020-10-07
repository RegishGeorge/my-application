package com.example.wandermate;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bus {
    @PrimaryKey(autoGenerate = true)
    private int bus_id;

    private String bus_name;

    public Bus(String bus_name) {
        this.bus_name = bus_name;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public int getBus_id() {
        return bus_id;
    }

    public String getBus_name() {
        return bus_name;
    }
}
