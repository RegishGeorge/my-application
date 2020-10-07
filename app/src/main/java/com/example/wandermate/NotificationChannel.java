package com.example.wandermate;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannel extends Application {
    public static final String SERVICE_CHANNEL_ID = "AlarmServiceChannel";
    public static final String ALARM_ALERT_CHANNEL_ID = "AlarmAlertChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel serviceChannel = new android.app.NotificationChannel(
                    SERVICE_CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager serviceManager = getSystemService(NotificationManager.class);
            serviceManager.createNotificationChannel(serviceChannel);

            android.app.NotificationChannel alarmAlertChannel = new android.app.NotificationChannel(
                    ALARM_ALERT_CHANNEL_ID,
                    "Alarm Alert Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            alarmAlertChannel.setSound(null,null);
            alarmAlertChannel.setLockscreenVisibility(1);
            NotificationManager alarmManager = getSystemService(NotificationManager.class);
            alarmManager.createNotificationChannel(alarmAlertChannel);
        }
    }
}
