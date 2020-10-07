package com.example.wandermate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WanderMateViewModel extends AndroidViewModel {
    private WanderMateRepository repository;
    private LiveData<List<OutputObject>> allServices;
    private LiveData<List<String>> allStops;
    private LiveData<List<Stop>> allCoordinates, coordinates;
    private LiveData<List<StopObject>> stopServices;
    private LiveData<List<ServiceDetailsObject>> serviceDetails;

    public WanderMateViewModel(@NonNull Application application) {
        super(application);
        repository = new WanderMateRepository(application);
        allStops = repository.getAllStops();
        allCoordinates = repository.getAllCoordinates();
    }

    public LiveData<List<OutputObject>> getAllServices(String start, String stop) {
        allServices = repository.getAllServices(start, stop);
        return allServices;
    }

    public LiveData<List<String>> getAllStops() {
        return allStops;
    }

    public LiveData<List<Stop>> getAllCoordinates() {
        return allCoordinates;
    }

    public LiveData<List<StopObject>> getStopServices(String start, int time_hr, int time_min) {
        stopServices = repository.getStopServices(start, time_hr, time_min);
        return stopServices;
    }

    public LiveData<List<Stop>> getCoordinates(String name) {
        coordinates = repository.getCoordinates(name);
        return coordinates;
    }

    public LiveData<List<ServiceDetailsObject>> getServiceDetails(int rid, int bid, String name) {
        serviceDetails = repository.getServiceDetails(rid, bid, name);
        return serviceDetails;
    }
}
