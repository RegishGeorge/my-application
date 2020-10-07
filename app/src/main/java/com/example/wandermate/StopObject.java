package com.example.wandermate;

public class StopObject {
    private int route_id;
    private int bus_id;
    private String bus_name;
    private String stop_name;
    private int departure_time_hrs;
    private int departure_time_mins;

    public StopObject(int route_id, int bus_id, String bus_name, String stop_name, int departure_time_hrs, int departure_time_mins) {
        this.route_id = route_id;
        this.bus_id = bus_id;
        this.bus_name = bus_name;
        this.stop_name = stop_name;
        this.departure_time_hrs = departure_time_hrs;
        this.departure_time_mins = departure_time_mins;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getBus_id() {
        return bus_id;
    }

    public String getBus_name() {
        return bus_name;
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
