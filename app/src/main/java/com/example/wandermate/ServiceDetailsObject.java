package com.example.wandermate;

public class ServiceDetailsObject {
    private String stop_name;
    private int departure_time_hrs;
    private int departure_time_mins;

    public ServiceDetailsObject(String stop_name, int departure_time_hrs, int departure_time_mins) {
        this.stop_name = stop_name;
        this.departure_time_hrs = departure_time_hrs;
        this.departure_time_mins = departure_time_mins;
    }

    public String getStop_name() {
        return stop_name;
    }

    public int getDeparture_time_hrs() {
        return departure_time_hrs;
    }

    public int getDeparture_time_mins() {
        return departure_time_mins;
    }
}
