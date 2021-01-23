package com.example.wandermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;

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

        editTxtFrom.addTextChangedListener(textWatcher);
        editTxtTo.addTextChangedListener(textWatcher);

        btnFindServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = editTxtFrom.getText().toString().trim();
                String stop = editTxtTo.getText().toString().trim();
                if(!stops.contains(start)) {
                    editTxtFrom.setError("Select a location from the drop down list");
                } else if(!stops.contains(stop)) {
                    editTxtTo.setError("Select a destination from the drop down list");
                } else {
                    closeKeyboard();
                    editTxtFrom.getText().clear();
                    editTxtTo.getText().clear();
                    editTxtFrom.clearFocus();
                    editTxtTo.clearFocus();
                    Intent intent = new Intent(getBaseContext(), FilteredServicesViewActivity.class);
                    intent.putExtra("Start", start);
                    intent.putExtra("Stop", stop);
                    startActivity(intent);
                }
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String from = editTxtFrom.getText().toString().trim();
            String to = editTxtTo.getText().toString().trim();
            btnFindServices.setEnabled(!from.isEmpty() && !to.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void addStops(List<String> stopList) {
        stops = new ArrayList<>(stopList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        editTxtFrom.setAdapter(adapter);
        editTxtTo.setAdapter(adapter);
    }
}


