package com.example.group13zoosearch;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Set<String> test = new HashSet<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      
        AnimalList animalList = new AnimalList();

        ListView listView = findViewById(R.id.exhibit_list);
        adapter
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                animalList.selected_exhibits);
        listView.setAdapter(adapter);
        loadProfile();

    }

    public void OnSearchClicked(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

  //leaving this in here because we will need a directions launch eventually
    public void directionsLaunched(View view) {
        Intent DirectionsIntent = new Intent(this, DirectionsActivity.class);
        startActivity(DirectionsIntent);
    }
  
    public void OnViewExhibitsClicked(View view) {
        Intent intent = new Intent(this, ViewExhibitsActivity.class);
        startActivity(intent);
    }

    public void OnDirectionClicked(View view) {
        Intent intent = new Intent(this, DirectionsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //code
        saveProfile();
    }

    public void loadProfile() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //code
//        SharedPreferences.Editor editor = preferences.edit();
        preferences.getStringSet("key", test);

        for (String value : test) {
            AnimalList.selected_exhibits.add(value);
        }

    }

    public void saveProfile() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //code
        for (String value : AnimalList.selected_exhibits) {
            test.add(value);
        }

        editor.putStringSet("key", test);

        editor.apply();
//        editor.commit();
    }


}