package com.example.wandermate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.ServiceDetailsHolder> {
    List<ServiceDetailsObject> serviceDetails = new ArrayList<>();


    @NonNull
    @Override
    public ServiceDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_details_item, parent, false);
        return new ServiceDetailsAdapter.ServiceDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceDetailsHolder holder, int position) {
        ServiceDetailsObject currentService = serviceDetails.get(position);
        String stopName = currentService.getStop_name();
        String departure_hrs, departure_mins;
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

        String time = departure_hrs + ":" + departure_mins;
        holder.txtStopName.setText(stopName);
        holder.txtTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return serviceDetails.size();
    }

    public void setServiceDetails(List<ServiceDetailsObject> serviceDetails) {
        this.serviceDetails = serviceDetails;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<ServiceDetailsObject> filteredList) {
        serviceDetails = filteredList;
        notifyDataSetChanged();
    }

    static class ServiceDetailsHolder extends RecyclerView.ViewHolder {
        private TextView txtStopName, txtTime;

        public ServiceDetailsHolder(@NonNull View itemView) {
            super(itemView);
            txtStopName = itemView.findViewById(R.id.txtStopName);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
