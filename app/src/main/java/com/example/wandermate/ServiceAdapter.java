package com.example.wandermate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
    private List<OutputObject> services = new ArrayList<>();

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item, parent, false);
        return new ServiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        OutputObject currentService = services.get(position);
        String departure_hrs, departure_mins, arrival_hrs, arrival_mins;
        if(currentService.getDeparture_time_hrs() < 10) {
            departure_hrs = "0" + currentService.getDeparture_time_hrs();
        } else {
            departure_hrs = Integer.toString(currentService.getDeparture_time_hrs());
        }
        if(currentService.getDeparture_time_mins() < 10) {
            departure_mins = "0" + currentService.getDeparture_time_mins();
        } else {
            departure_mins = Integer.toString(currentService.getDeparture_time_mins());
        }
        if(currentService.getArrival_time_hrs() < 10) {
            arrival_hrs = "0" + currentService.getArrival_time_hrs();
        } else {
            arrival_hrs = Integer.toString(currentService.getArrival_time_hrs());
        }
        if(currentService.getArrival_time_mins() < 10) {
            arrival_mins = "0" + currentService.getArrival_time_mins();
        } else {
            arrival_mins = Integer.toString(currentService.getArrival_time_mins());
        }
        String departure = departure_hrs + ":" + departure_mins;
        String arrival = arrival_hrs + ":" + arrival_mins;
        String time = departure + " - " + arrival;
        holder.txtServiceName.setText(currentService.getBus_name());
        holder.txtTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void setRoutes(List<OutputObject> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    static class ServiceHolder extends RecyclerView.ViewHolder {
        private TextView txtServiceName, txtTime;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
