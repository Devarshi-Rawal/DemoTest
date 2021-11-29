package com.example.demotest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PrivacyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_details);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}