package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.List;

public class ServiceDetailsActivity extends AppCompatActivity {
    int route_id, bus_id;
    String start;
    private WanderMateViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        recyclerView = findViewById(R.id.recyclerView);

        route_id = getIntent().getIntExtra("route_id", 0);
        bus_id = getIntent().getIntExtra("bus_id", 0);
        start = getIntent().getStringExtra("Start");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ServiceDetailsAdapter adapter = new ServiceDetailsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getServiceDetails(route_id, bus_id, start).observe(this, new Observer<List<ServiceDetailsObject>>() {
            @Override
            public void onChanged(List<ServiceDetailsObject> serviceDetailsObjects) {
                adapter.setServiceDetails(serviceDetailsObjects);
            }
        });
    }
}