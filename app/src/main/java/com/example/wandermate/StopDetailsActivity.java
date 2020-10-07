package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class StopDetailsActivity extends AppCompatActivity {
    public static String START_NAME;
    private WanderMateViewModel viewModel;
    private TextView txtNoService, txtTitle;
    private CardView cardView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_details);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        START_NAME = getIntent().getStringExtra("Stop Name");
        String timeString_hr = getIntent().getStringExtra("Time Hour");
        String timeString_min = getIntent().getStringExtra("Time Min");
        int time_hr = Integer.parseInt(timeString_hr);
        int time_min = Integer.parseInt(timeString_min);

        txtNoService = findViewById(R.id.txtNoService);
        txtTitle = findViewById(R.id.txtTitle);
        cardView = findViewById(R.id.cardView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StopServiceAdapter adapter = new StopServiceAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getStopServices(START_NAME, time_hr, time_min).observe(this, new Observer<List<StopObject>>() {
            @Override
            public void onChanged(List<StopObject> stopServices) {
                if(stopServices.size()==0) {
                    txtTitle.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    txtNoService.setVisibility(View.VISIBLE);
                } else {
                    txtNoService.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setStopServices(stopServices);
                }
            }
        });
    }
}