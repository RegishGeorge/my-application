package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class FilteredServicesViewActivity extends AppCompatActivity {
    private static final String TAG = "FilteredServicesView";
    private WanderMateViewModel viewModel;
    private TextView txtNoService, txtTitle;
    private CardView cardView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_services_view);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        String start = getIntent().getStringExtra("Start");
        String stop = getIntent().getStringExtra("Stop");
        Log.d(TAG, "onCreate: " + start + " " + stop);

        txtNoService = findViewById(R.id.txtNoService);
        txtTitle = findViewById(R.id.txtTitle);
        cardView = findViewById(R.id.cardView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ServiceAdapter adapter = new ServiceAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllServices(start, stop).observe(this, new Observer<List<OutputObject>>() {
            @Override
            public void onChanged(List<OutputObject> services) {
                if(services.size()==0) {
                    txtTitle.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    txtNoService.setVisibility(View.VISIBLE);
                } else {
                    txtNoService.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setRoutes(services);
                }
            }
        });
    }
}