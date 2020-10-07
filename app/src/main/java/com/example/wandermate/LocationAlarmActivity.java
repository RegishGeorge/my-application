package com.example.wandermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static com.example.wandermate.AlarmService.SERVICE_ACTIVE;

public class LocationAlarmActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "LocationAlarmActivity";
    GoogleMap mMap;
    private AutoCompleteTextView editTextDestination;
    private WanderMateViewModel viewModel;
    private Button btnShow, btnSet;
    private ArrayList<String> stops;
    private ArrayAdapter<String> adapter;
    private Marker marker = null;
    LocationRequest locationRequest;
    LatLng latLngStop;
    String stopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_alarm);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAlarm);
        mapFragment.getMapAsync(this);

        editTextDestination = findViewById(R.id.editTextDestination);
        btnShow = findViewById(R.id.btnShow);
        btnSet = findViewById(R.id.btnSet);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(SERVICE_ACTIVE) {
            btnSet.setEnabled(false);
        }

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllStops().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stopList) {
                addStops(stopList);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = editTextDestination.getText().toString().trim();
                viewModel.getCoordinates(destination).observe(LocationAlarmActivity.this, new Observer<List<Stop>>() {
                    @Override
                    public void onChanged(List<Stop> stops) {
                        if(stops.size() == 1) {
                            latLngStop = new LatLng(stops.get(0).getStop_latitude(), stops.get(0).getStop_longitude());
                            stopName = stops.get(0).getStop_name();
                            btnSet.setVisibility(View.VISIBLE);
                            if(marker != null) {
                                marker.remove();
                            }
                            marker = mMap.addMarker(new MarkerOptions()
                                    .title(stopName)
                                    .position(latLngStop));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngStop, 15));
                        } else {
                            Toast.makeText(LocationAlarmActivity.this, "Please enter a valid destination", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSettingsAndStartLocationUpdates();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void addStops(List<String> stopList) {
        stops = new ArrayList<>(stopList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        editTextDestination.setAdapter(adapter);
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
                Intent serviceIntent = new Intent(LocationAlarmActivity.this, AlarmService.class);
                Toast.makeText(LocationAlarmActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
                serviceIntent.putExtra("Latitude", latLngStop.latitude);
                serviceIntent.putExtra("Longitude", latLngStop.longitude);
                serviceIntent.putExtra("Name", stopName);
                ContextCompat.startForegroundService(LocationAlarmActivity.this, serviceIntent);
                finish();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(LocationAlarmActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}