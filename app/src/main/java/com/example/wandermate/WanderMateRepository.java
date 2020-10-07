package com.example.wandermate;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WanderMateRepository {
    private WanderMateDao wanderMateDao;
    private LiveData<List<OutputObject>> allServices;
    private LiveData<List<String>> allStops;
    private LiveData<List<Stop>> allCoordinates, coordinates;
    private LiveData<List<StopObject>> stopServices;
    private LiveData<List<ServiceDetailsObject>> serviceDetails;

    public WanderMateRepository(Application application) {
        WanderMateDatabase database = WanderMateDatabase.getInstance(application);
        wanderMateDao = database.wanderMateDao();
        allStops = wanderMateDao.getAllStops();
        allCoordinates = wanderMateDao.getAllCoordinates();
    }

    public LiveData<List<OutputObject>> getAllServices(String start, String stop) {
        allServices = wanderMateDao.getAllServices(start, stop);
        return allServices;
    }

    public LiveData<List<String>> getAllStops() {
        return allStops;
    }

    public LiveData<List<Stop>> getAllCoordinates() {
        return allCoordinates;
    }

    public LiveData<List<StopObject>> getStopServices(String start, int time_hr, int time_min) {
        stopServices = wanderMateDao.getStopServices(start, time_hr, time_min);
        return stopServices;
    }

    public LiveData<List<Stop>> getCoordinates(String name) {
        coordinates = wanderMateDao.getCoordinates(name);
        return coordinates;
    }

    public LiveData<List<ServiceDetailsObject>> getServiceDetails(int rid, int bid, String name) {
        serviceDetails = wanderMateDao.getServiceDetails(rid, bid, name);
        return serviceDetails;
    }

}
