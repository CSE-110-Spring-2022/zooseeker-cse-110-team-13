package com.example.group13zoosearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private Set<String> test = new HashSet<>();
    public RecyclerView recyclerView;
    private Map<String, AnimalNode> animalNodes;
    private PriorityQueue<AnimalNode> selectedAnimals;
    AnimalNodeAdapter animalAdapter;

    //testing some refactoring
    public AnimalList info;         //this animalList will hold all list objects in it


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = new AnimalList(this, "exhibit_info.json", "trail_info.json", "zoo_graph");

        animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.animal_node_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");

        loadProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //This was me messing around with a recycler view on the main activity page
        // we can decide to remove this later
        selectedAnimals = info.generatePriorityQueue();
        ArrayList<AnimalNode> temp = new ArrayList<AnimalNode>(selectedAnimals);
        animalAdapter.setAnimalNodeList(temp);
    }

    public void OnSearchClicked(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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

    public void OnRoutePlanClicked(View view) {
        Intent intent = new Intent(this, RoutePlanActivity.class);
        startActivity(intent);
    }
}