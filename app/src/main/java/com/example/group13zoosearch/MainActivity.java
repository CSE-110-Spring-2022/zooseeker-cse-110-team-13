package com.example.group13zoosearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void directionsLaunched(View view) {
        Intent DirectionsIntent = new Intent(this, DirectionsActivity.class);
        startActivity(DirectionsIntent);
    }

    public void searchLaunched(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }
}