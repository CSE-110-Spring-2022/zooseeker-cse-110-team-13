package com.example.group13zoosearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
    Button saveBtn;

    //testing some refactoring
    public AnimalList info;         //this animalList will hold all list objects in it


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBtn = findViewById(R.id.save_btn);
        loadExhibits();
        info = new AnimalList(this, "exhibit_info.json", "trail_info.json", "zoo_graph");

        animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.animal_node_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to save data in shared prefs.
                saveExhibits();
            }
        });
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


    /**
     * using json and gson in sharedpreference to save and load
     *
     * modified from online sources geeksforgeeks
     * URL: https://www.geeksforgeeks.org/how-to-save-arraylist-to-sharedpreferences-in-android/
     */

    private void loadExhibits() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exhibits list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        AnimalList.selected_exhibits = gson.fromJson(json, type);

        if (AnimalList.selected_exhibits == null)
            AnimalList.selected_exhibits = new ArrayList<>();

    }

    private void saveExhibits() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AnimalList.selected_exhibits);
        editor.putString("exhibits list", json);
        editor.apply();

        Toast.makeText(this, "Saved Selected Exhibits List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    public void OnRoutePlanClicked(View view) {
        Intent intent = new Intent(this, RoutePlanActivity.class);
        startActivity(intent);
    }
}