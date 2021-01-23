package com.example.wandermate;

import android.os.Parcel;
import android.os.Parcelable;

public class StopObject implements Parcelable {
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

    protected StopObject(Parcel in) {
        route_id = in.readInt();
        bus_id = in.readInt();
        bus_name = in.readString();
        stop_name = in.readString();
        departure_time_hrs = in.readInt();
        departure_time_mins = in.readInt();
    }

    public static final Creator<StopObject> CREATOR = new Creator<StopObject>() {
        @Override
        public StopObject createFromParcel(Parcel in) {
            return new StopObject(in);
        }

        @Override
        public StopObject[] newArray(int size) {
            return new StopObject[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(route_id);
        dest.writeInt(bus_id);
        dest.writeString(bus_name);
        dest.writeString(stop_name);
        dest.writeInt(departure_time_hrs);
        dest.writeInt(departure_time_mins);
    }
}
