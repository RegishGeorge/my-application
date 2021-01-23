package com.example.wandermate;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceDetailsObject implements Parcelable {
    private String stop_name;
    private int departure_time_hrs;
    private int departure_time_mins;

    public ServiceDetailsObject(String stop_name, int departure_time_hrs, int departure_time_mins) {
        this.stop_name = stop_name;
        this.departure_time_hrs = departure_time_hrs;
        this.departure_time_mins = departure_time_mins;
    }

    protected ServiceDetailsObject(Parcel in) {
        stop_name = in.readString();
        departure_time_hrs = in.readInt();
        departure_time_mins = in.readInt();
    }

    public static final Creator<ServiceDetailsObject> CREATOR = new Creator<ServiceDetailsObject>() {
        @Override
        public ServiceDetailsObject createFromParcel(Parcel in) {
            return new ServiceDetailsObject(in);
        }

        @Override
        public ServiceDetailsObject[] newArray(int size) {
            return new ServiceDetailsObject[size];
        }
    };

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
        dest.writeString(stop_name);
        dest.writeInt(departure_time_hrs);
        dest.writeInt(departure_time_mins);
    }
}
