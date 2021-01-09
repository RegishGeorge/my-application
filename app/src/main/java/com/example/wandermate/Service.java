package com.example.wandermate;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(foreignKeys = {@ForeignKey(entity = Route.class, parentColumns = "route_id", childColumns = "route_id"),
@ForeignKey(entity = Bus.class,  parentColumns = "bus_id", childColumns = "bus_id")},
primaryKeys = {"route_id", "bus_id", "start_time_hrs", "start_time_mins"})
public class Service {
    private int route_id;
    private int bus_id;
    private int start_time_hrs;
    private int start_time_mins;

    public Service(int route_id, int bus_id, int start_time_hrs, int start_time_mins) {
        this.route_id = route_id;
        this.bus_id = bus_id;
        this.start_time_hrs = start_time_hrs;
        this.start_time_mins = start_time_mins;
    }

    public int getRoute_id() {
        return route_id;
    }

    public int getBus_id() {
        return bus_id;
    }

    public int getStart_time_hrs() {
        return start_time_hrs;
    }

    public int getStart_time_mins() {
        return start_time_mins;
    }
}
