package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StopAlarmActivity extends AppCompatActivity {
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(StopAlarmActivity.this, AlarmService.class);
                stopService(serviceIntent);
                Toast.makeText(StopAlarmActivity.this, "Alarm stopped", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}