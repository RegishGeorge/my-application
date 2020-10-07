package com.example.wandermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import static com.example.wandermate.AlarmService.SERVICE_ACTIVE;

public class FilteredServicesViewActivity extends AppCompatActivity {
    private static final String TAG = "FilteredServicesView";
    private WanderMateViewModel viewModel;
    private TextView txtNoService, txtTitle;
    private CardView cardView;
    private RecyclerView recyclerView;
    private Button btnSet;
    LocationRequest locationRequest;
    LatLng latLngStop;
    String stopName;

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
        btnSet = findViewById(R.id.btnSet);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        stopName = stop;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ServiceAdapter adapter = new ServiceAdapter();
        recyclerView.setAdapter(adapter);

        if(SERVICE_ACTIVE) {
            btnSet.setEnabled(false);
        }

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllServices(start, stop).observe(this, new Observer<List<OutputObject>>() {
            @Override
            public void onChanged(List<OutputObject> services) {
                if(services.size()==0) {
                    txtTitle.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    btnSet.setVisibility(View.GONE);
                    txtNoService.setVisibility(View.VISIBLE);
                } else {
                    txtNoService.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    btnSet.setVisibility(View.VISIBLE);
                    adapter.setRoutes(services);
                }
            }
        });

        viewModel.getCoordinates(stopName).observe(this, new Observer<List<Stop>>() {
            @Override
            public void onChanged(List<Stop> stops) {
                if(stops.size()==1) {
                    latLngStop = new LatLng(stops.get(0).getStop_latitude(), stops.get(0).getStop_longitude());
                }
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSettingsAndStartLocationUpdates();
            }
        });
    }

    private void checkSettingsAndStartLocationUpdates() {
        Log.d(TAG, "checkSettingsAndStartLocationUpdates: Function started");
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, "checkSettingsAndStartLocationUpdates: Service Started");
                Intent serviceIntent = new Intent(FilteredServicesViewActivity.this, AlarmService.class);
                Toast.makeText(FilteredServicesViewActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
                serviceIntent.putExtra("Latitude", latLngStop.latitude);
                serviceIntent.putExtra("Longitude", latLngStop.longitude);
                serviceIntent.putExtra("Name", stopName);
                ContextCompat.startForegroundService(FilteredServicesViewActivity.this, serviceIntent);
                finish();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(FilteredServicesViewActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}