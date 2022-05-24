package com.example.group13zoosearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RoutePlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        //TODO: call Directions.routePlan() and set up display will likely need a recycler view (modify AnimalNode Adapter to also work for this :) )
    }

    public void returnToHome(View view) {
        finish();
    }

    public void toDirections(View view) {
        Intent intent = new Intent(this, DirectionsActivity.class);
        startActivity(intent);
        finish();
    }
}