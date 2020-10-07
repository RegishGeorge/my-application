package com.example.wandermate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.wandermate.StopDetailsActivity.START_NAME;

public class StopServiceAdapter extends RecyclerView.Adapter<StopServiceAdapter.StopServiceHolder> {
    private List<StopObject> stopServices = new ArrayList<>();
    private Context mContext;

    public StopServiceAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public StopServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop_service_item, parent, false);
        return new StopServiceAdapter.StopServiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StopServiceHolder holder, final int position) {
        StopObject currentStopService = stopServices.get(position);
        final String serviceName = currentStopService.getBus_name() + " - " + currentStopService.getStop_name();
        String departure_hrs, departure_mins;
        if(currentStopService.getDeparture_time_hrs() < 10) {
            departure_hrs = "0" + currentStopService.getDeparture_time_hrs();
        } else {
            departure_hrs = Integer.toString(currentStopService.getDeparture_time_hrs());
        }
        if(currentStopService.getDeparture_time_mins() < 10) {
            departure_mins = "0" + currentStopService.getDeparture_time_mins();
        } else {
            departure_mins = Integer.toString(currentStopService.getDeparture_time_mins());
        }
        String time = departure_hrs + ":" + departure_mins;
        holder.txtServiceName.setText(serviceName);
        holder.txtTime.setText(time);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
                intent.putExtra("route_id", stopServices.get(position).getRoute_id());
                intent.putExtra("bus_id", stopServices.get(position).getBus_id());
                intent.putExtra("Start", START_NAME);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stopServices.size();
    }

    public void setStopServices(List<StopObject> stopServices) {
        this.stopServices = stopServices;
        notifyDataSetChanged();
    }

    static class StopServiceHolder extends RecyclerView.ViewHolder {
        private TextView txtServiceName, txtTime;
        private CardView parent;

        public StopServiceHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
