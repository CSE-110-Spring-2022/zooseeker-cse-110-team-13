package com.example.group13zoosearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    FusedLocationProviderClient client;
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
//get location
        client = LocationServices.getFusedLocationProviderClient(this);

        //We check permissions to access our current location for our activity
        //We will then run our functions to generate the routes
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();


        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
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
        ArrayList<AnimalNode> temp = info.generateArrayList(info.getCurrentLocation());
        temp = Directions.computeRoute("entrance_exit_gate",temp, info.getZoo_graph_info());
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

//    public void OnMapClicked(View view)
//    {
//        Intent intent = new Intent(this,MapActivity.class);
//        startActivity(intent);
//    }
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

    //Get Current Location Function
    private void getCurrentLocation(){
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){

                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            DirectionsFactory.currLL = latLng;


                }

            }
        });

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
    public void clearExhibits(View view) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        String json = sharedPreferences.getString("exhibits list", null);
        AnimalList.selected_exhibits.clear();
        animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.animal_node_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");
        Toast.makeText(this, "Cleared Selected Exhibits List. ", Toast.LENGTH_SHORT).show();
    }
    public void OnRoutePlanClicked(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
