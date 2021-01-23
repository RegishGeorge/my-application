package com.example.wandermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StopDetailsActivity extends AppCompatActivity {
    private static final String TAG = "StopDetailsActivity";
    public static String START_NAME;
    private WanderMateViewModel viewModel;
    private TextView txtNoService, txtTitle, txtNavigate, txtNoResults;
    private CardView cardView;
    private RecyclerView recyclerView;
    private Button btnNavigate;
    private EditText txtSearch;
    private List<StopObject> stopServicesList;
    private StopServiceAdapter adapter;
    private ArrayList<StopObject> filteredList;

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
        Bundle bundle = getIntent().getParcelableExtra("Stop Position");
        final LatLng position = bundle.getParcelable("position");
        String timeString_hr = getIntent().getStringExtra("Time Hour");
        String timeString_min = getIntent().getStringExtra("Time Min");
        int time_hr = Integer.parseInt(timeString_hr);
        int time_min = Integer.parseInt(timeString_min);

        txtNoService = findViewById(R.id.txtNoService);
        txtTitle = findViewById(R.id.txtTitle);
        cardView = findViewById(R.id.cardView);
        btnNavigate = findViewById(R.id.btnNavigate);
        txtNavigate = findViewById(R.id.txt_navigate);
        txtSearch = findViewById(R.id.txtSearch1);
        txtNoResults = findViewById(R.id.txt_no_results);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new StopServiceAdapter(this, position);
        stopServicesList = new ArrayList<>();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        if(savedInstanceState == null) {
            viewModel.getStopServices(START_NAME, time_hr, time_min).observe(this, new Observer<List<StopObject>>() {
                @Override
                public void onChanged(List<StopObject> stopServices) {
                    if(stopServices.size()==0) {
                        txtTitle.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        btnNavigate.setVisibility(View.GONE);
                        txtNavigate.setVisibility(View.GONE);
                        txtSearch.setVisibility(View.GONE);
                        txtNoResults.setVisibility(View.GONE);
                        txtNoService.setVisibility(View.VISIBLE);
                    } else {
                        txtNoService.setVisibility(View.GONE);
                        txtNoResults.setVisibility(View.GONE);
                        txtTitle.setVisibility(View.VISIBLE);
                        cardView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        btnNavigate.setVisibility(View.VISIBLE);
                        txtNavigate.setVisibility(View.VISIBLE);
                        txtSearch.setVisibility(View.VISIBLE);
                        stopServicesList = stopServices;
                        adapter.setStopServices(stopServices);
                    }
                }
            });
        }
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        if(savedInstanceState != null) {
            Log.d(TAG, "onCreate: recreating activity");
            stopServicesList = (ArrayList<StopObject>)savedInstanceState.getSerializable("actual_list");
            txtSearch.setText(savedInstanceState.getString("destination"));
            Log.d(TAG, "onCreate: text has been set");
        }

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = "&destination="+ position.latitude +"%2C+"+ position.longitude;
                String travelMode = "&travelmode=walking";
                String dirAction = "&dir_action=navigate";
                String url = "https://www.google.com/maps/dir/?api=1"+ destination + travelMode + dirAction;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void filter(String text) {
        Log.d(TAG, "filter: filter function called with the text " + text);
        filteredList = new ArrayList<>();
        Log.d(TAG, "filter: size of actual data is " + stopServicesList.size());
        for(StopObject i : stopServicesList) {
            if(i.getStop_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(i);
            }
        }
        Log.d(TAG, "filter: size of filtered list is " + filteredList.size());
        adapter.filterList(filteredList);
        Log.d(TAG, "filter: adapter called");
        if(filteredList.size() == 0) {
            cardView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            txtNoResults.setVisibility(View.VISIBLE);
            Log.d(TAG, "filter: invisible");
        } else {
            cardView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            txtNoResults.setVisibility(View.GONE);
            Log.d(TAG, "filter: visible");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("destination", txtSearch.getText().toString().trim());
        outState.putSerializable("actual_list", (Serializable)stopServicesList);
        Log.d(TAG, "onSaveInstanceState: saving data");
        super.onSaveInstanceState(outState);
    }
}