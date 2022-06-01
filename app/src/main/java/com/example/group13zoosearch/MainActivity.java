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
        info = new AnimalList(this, "exhibit_info.json", "trail_info.json", "zoo_graph.json");

        animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(false);

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

        //Displays current route plan
        selectedAnimals = info.generatePriorityQueue();
        ArrayList<AnimalNode> temp = info.generateArrayList(info.getCurrentLocation());
        temp = Directions.computeRoute(info.getCurrentLocation(),temp, info.getZoo_graph_info());
        animalAdapter.setAnimalNodeList(temp);
        //TODO Fix recycler view visual bug (Only shows 7 animals then starts to repeat)
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

    public void OnMapClicked(View view)
    {
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //code
        //saveProfile();
    }
    public void loadProfile() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //code
//        SharedPreferences.Editor editor = preferences.edit();
        preferences.getStringSet("key", test);
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
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}