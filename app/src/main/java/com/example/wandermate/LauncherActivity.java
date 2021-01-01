package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                if(SERVICE_ACTIVE) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast_layout));
                    ImageView imageView = layout.findViewById(R.id.imageView);
                    TextView textView = layout.findViewById(R.id.txt_message);
                    imageView.setImageResource(R.drawable.ic_lock);
                    textView.setText("Stop the currently active alarm to access location alarm services");
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                } else {
                    Intent intent = new Intent(LauncherActivity.this, LocationAlarmActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}