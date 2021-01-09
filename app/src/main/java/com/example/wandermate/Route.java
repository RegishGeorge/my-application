package com.example.wandermate;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Route {
    @PrimaryKey
    private int route_id;

    private String route_name;

    private int stops_number;

    public Route(int route_id, String route_name, int stops_number) {
        this.route_id = route_id;
        this.route_name = route_name;
        this.stops_number = stops_number;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public int getStops_number() {
        return stops_number;
    }
}
