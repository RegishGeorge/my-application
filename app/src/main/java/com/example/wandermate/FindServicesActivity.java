package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class FindServicesActivity extends AppCompatActivity {
    private static final String TAG = "FindServicesActivity";
    private Button btnFindServices;
    private AutoCompleteTextView editTxtFrom, editTxtTo;
    private WanderMateViewModel viewModel;
    private ArrayList<String> stops;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_services);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();

        btnFindServices = findViewById(R.id.btnFindServices);
        editTxtFrom = findViewById(R.id.editTxtFrom);
        editTxtTo = findViewById(R.id.editTxtTo);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllStops().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stopList) {
                addStops(stopList);
            }
        });

        btnFindServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = editTxtFrom.getText().toString().trim();
                String stop = editTxtTo.getText().toString().trim();
                if(start.isEmpty() || stop.isEmpty()) {
                    Toast.makeText(FindServicesActivity.this, "Please provide both the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    editTxtFrom.getText().clear();
                    editTxtTo.getText().clear();
                    Intent intent = new Intent(getBaseContext(), FilteredServicesViewActivity.class);
                    intent.putExtra("Start", start);
                    intent.putExtra("Stop", stop);
                    startActivity(intent);
                }
            }
        });
    }

    private void addStops(List<String> stopList) {
        stops = new ArrayList<>(stopList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        editTxtFrom.setAdapter(adapter);
        editTxtTo.setAdapter(adapter);
    }
}


