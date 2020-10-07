package com.example.wandermate;

public class OutputObject {
    private String bus_name;
    private int departure_time_hrs;
    private int departure_time_mins;
    private int arrival_time_hrs;
    private int arrival_time_mins;

    public OutputObject(String bus_name, int departure_time_hrs, int departure_time_mins, int arrival_time_hrs, int arrival_time_mins) {
        this.bus_name = bus_name;
        this.departure_time_hrs = departure_time_hrs;
        this.departure_time_mins = departure_time_mins;
        this.arrival_time_hrs = arrival_time_hrs;
        this.arrival_time_mins = arrival_time_mins;
    }

    public String getBus_name() {
        return bus_name;
    }

    public int getDeparture_time_hrs() {
        return departure_time_hrs;
    }

    public int getDeparture_time_mins() {
        return departure_time_mins;
    }

    public int getArrival_time_hrs() {
        return arrival_time_hrs;
    }

    public int getArrival_time_mins() {
        return arrival_time_mins;
    }
}
