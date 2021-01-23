package com.example.wandermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsActivity extends AppCompatActivity {
    int route_id, bus_id;
    String start;
    private Button btnNavigate;
    private WanderMateViewModel viewModel;
    private RecyclerView recyclerView;
    private CardView cardView;
    private EditText txtSearch;
    private TextView txtNoResults;
    private ServiceDetailsAdapter adapter;
    private List<ServiceDetailsObject> serviceDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        Bundle bundle = getIntent().getParcelableExtra("Stop Position");
        final LatLng position = bundle.getParcelable("position");

        recyclerView = findViewById(R.id.recyclerView);
        btnNavigate = findViewById(R.id.btnNavigate);
        txtSearch = findViewById(R.id.txtSearch);
        txtNoResults = findViewById(R.id.txt_no_results);
        cardView = findViewById(R.id.cardView);

        route_id = getIntent().getIntExtra("route_id", 0);
        bus_id = getIntent().getIntExtra("bus_id", 0);
        start = getIntent().getStringExtra("Start");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ServiceDetailsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);

        if(savedInstanceState == null) {
            viewModel.getServiceDetails(route_id, bus_id, start).observe(this, new Observer<List<ServiceDetailsObject>>() {
                @Override
                public void onChanged(List<ServiceDetailsObject> serviceDetailsObjects) {
                    serviceDetailsList = serviceDetailsObjects;
                    adapter.setServiceDetails(serviceDetailsObjects);
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
            serviceDetailsList = (ArrayList<ServiceDetailsObject>)savedInstanceState.getSerializable("actual_list");
            txtSearch.setText(savedInstanceState.getString("destination"));
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
        ArrayList<ServiceDetailsObject> filteredList = new ArrayList<>();
        for(ServiceDetailsObject i : serviceDetailsList) {
            if(i.getStop_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(i);
            }
        }
        adapter.filterList(filteredList);
        if(filteredList.size() == 0) {
            cardView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            txtNoResults.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            txtNoResults.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("destination", txtSearch.getText().toString().trim());
        outState.putSerializable("actual_list", (Serializable)serviceDetailsList);
        super.onSaveInstanceState(outState);
    }
}