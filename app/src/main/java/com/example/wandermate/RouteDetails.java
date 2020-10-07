package com.example.wandermate;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(foreignKeys = {@ForeignKey(entity = Route.class, parentColumns = "route_id", childColumns = "route_id"),
@ForeignKey(entity = Stop.class, parentColumns = "stop_id", childColumns = "stop_id")},
primaryKeys = {"route_id", "stop_id"})
public class RouteDetails {
    private int route_id;
    private int stop_id;
    private int stop_number;
    private int duration_hrs;
    private int duration_mins;

    public RouteDetails(int route_id, int stop_id, int stop_number, int duration_hrs, int duration_mins) {
        this.route_id = route_id;
        this.stop_id = stop_id;
        this.stop_number = stop_number;
        this.duration_hrs = duration_hrs;
        this.duration_mins = duration_mins;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getStop_id() {
        return stop_id;
    }

    public int getStop_number() {
        return stop_number;
    }

    public int getDuration_hrs() {
        return duration_hrs;
    }

    public int getDuration_mins() {
        return duration_mins;
    }
}
