package com.example.wandermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NearbyServicesActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private static final String TAG = "NearbyServicesActivity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Button btnFind, btnEnable;
    private WanderMateViewModel viewModel;
    private ArrayList<Stop> coordinates;
    private String stop = "";
    private LatLng position;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_services);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnFind = findViewById(R.id.btnFind);
        btnEnable = findViewById(R.id.btnEnable);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllCoordinates().observe(this, new Observer<List<Stop>>() {
            @Override
            public void onChanged(List<Stop> coordinatesList) {
                addCoordinates(coordinatesList);
            }
        });

        btnEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationSettings();
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(coordinates.size() != 0) {
                            int flag = 0;
                            for(Stop stop: coordinates) {
                                float[] results = new float[1];
                                Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                                        stop.getStop_latitude(), stop.getStop_longitude(), results);
                                if(results[0]<1000) {
                                    flag = 1;
                                    mMap.addMarker(new MarkerOptions()
                                            .title(stop.getStop_name())
                                            .snippet("Click to view more details.")
                                            .position(new LatLng(stop.getStop_latitude(), stop.getStop_longitude())));
                                }
                            }
                            if(flag==0) {
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast_layout));
                                ImageView imageView = layout.findViewById(R.id.imageView);
                                TextView textView = layout.findViewById(R.id.txt_message);
                                imageView.setImageResource(R.drawable.ic_emoji_sad);
                                textView.setText("No stops nearby");
                                final Toast toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        checkLocationSettings();
    }

    private void checkLocationSettings() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Settings of device are satisfied
                enableUserLocation();
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast_layout));
                ImageView imageView = layout.findViewById(R.id.imageView);
                TextView textView = layout.findViewById(R.id.txt_message);
                imageView.setImageResource(R.drawable.ic_gps);
                textView.setText("Fetching location...");
                final Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zoomToUserLocation();
                        btnEnable.setVisibility(View.GONE);
                        btnFind.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(NearbyServicesActivity.this, 1001);
                        btnEnable.setVisibility(View.VISIBLE);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void enableUserLocation() {
        mMap.setMyLocationEnabled(true);
    }

    private void zoomToUserLocation() {
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        });
    }

    private void addCoordinates(List<Stop> coordinatesList) {
        coordinates = new ArrayList<>(coordinatesList);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(stop.equals(marker.getTitle())) {
            Date currentTime = Calendar.getInstance().getTime();
            DateFormat date_hr = new SimpleDateFormat("HH");
            DateFormat date_min = new SimpleDateFormat("mm");
            String time_hr = date_hr.format(currentTime);
            String time_min = date_min.format(currentTime);
            Intent intent = new Intent(this, StopDetailsActivity.class);
            intent.putExtra("Stop Name", stop);
            Bundle args = new Bundle();
            args.putParcelable("position", position);
            intent.putExtra("Stop Position", args);
            intent.putExtra("Time Hour", time_hr);
            intent.putExtra("Time Min", time_min);
            startActivity(intent);
        }
        stop = marker.getTitle();
        position = marker.getPosition();
        return false;
    }
}