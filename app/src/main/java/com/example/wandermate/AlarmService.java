package com.example.wandermate;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import static com.example.wandermate.NotificationChannel.ALARM_ALERT_CHANNEL_ID;
import static com.example.wandermate.NotificationChannel.SERVICE_CHANNEL_ID;

public class AlarmService extends Service {
    public static boolean SERVICE_ACTIVE = false;
    private static final String TAG = "AlarmService";
    double latitude, longitude;
    String stopName;
    FusedLocationProviderClient fusedLocationProviderClient;
    Ringtone ringtone = null;
    long[] pattern = {0, 250, 250, 250};
    Vibrator vibrator = null;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(locationResult == null) {
                return;
            }
            for(Location location: locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + location.toString());
                float[] results = new float[1];
                Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        latitude, longitude, results);
                Log.d(TAG, "onLocationResult: " + results[0]);
                if(results[0]<1000) {
                    displayNotification();
                    if(ringtone == null) {
                        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
                        ringtone.play();
                    }
                    if(vibrator == null) {
                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(pattern, 1);
                    }
                    stopLocationUpdates();
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service created");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SERVICE_ACTIVE = true;
        Log.d(TAG, "onStartCommand: Service beginning");
        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);
        stopName = intent.getStringExtra("Name");
        Intent notificationIntent = new Intent(this, StopAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
                .setContentTitle("Alarm Service")
                .setContentText("Location alarm set for " + stopName)
                .setSmallIcon(R.mipmap.wander_mate)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        Log.d(TAG, "onStartCommand: Notification built");
        startAlarm();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        SERVICE_ACTIVE = false;
        if(ringtone != null) {
            ringtone.stop();
            ringtone = null;
        }
        if(vibrator != null) {
            vibrator.cancel();
            vibrator = null;
        }
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    private void startAlarm() {
        Log.d(TAG, "startAlarm: Function called");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void displayNotification() {
        Intent notificationIntent = new Intent(this, StopAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("Stop", true);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, ALARM_ALERT_CHANNEL_ID)
                .setContentTitle("Reached location!")
                .setContentText("Click to stop alarm")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.RED)
                .addAction(R.mipmap.ic_launcher, "Stop Alarm", actionIntent)
                .build();
        WakeLocker.acquire(this);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, notification);
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
