package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.wandermate.AlarmService.SERVICE_ACTIVE;

public class LauncherActivity extends AppCompatActivity {

    private Button btnFind, btnNearby, btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        btnFind = findViewById(R.id.btnFind);
        btnNearby = findViewById(R.id.btnNearby);
        btnAlarm = findViewById(R.id.btnAlarm);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, FindServicesActivity.class);
                startActivity(intent);
            }
        });

        btnNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, NearbyServicesActivity.class);
                startActivity(intent);
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, LocationAlarmActivity.class);
                startActivity(intent);
            }
        });
    }
}