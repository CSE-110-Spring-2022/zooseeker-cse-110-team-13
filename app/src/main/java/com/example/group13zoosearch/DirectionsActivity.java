package com.example.group13zoosearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DirectionsActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private PriorityQueue<AnimalNode> selectedAnimals;
    private Queue<AnimalNode> visitedAnimals;
    private Map<String, AnimalNode> animalNodes;
    private Map<String, EdgeNameItem> edgeNodes;
    private Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    private List<String> directions;
    private String currentLocation;
    private TextView animalToVisit;
    private boolean detailed;
    private boolean noSelectedAnimals;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        listAdapter = new ListAdapter();
        listAdapter.setHasStableIds(true);

        //recycler view
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        //CREATING LISTS:
        //create AnimalNode list   TODO: create and store these in AnimalList at launch so every activity has access to these
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(this, "trail_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(this, "zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());

        //Fetching selected animals
        AnimalList anList = new AnimalList(this, "exhibit_info.json","trail_info.json","zoo_graph.json");
        selectedAnimals = anList.generatePriorityQueue();
        Log.d("Animal Queue created", selectedAnimals.toString());

        //SETTING UP FIRST DIRECTION LIST
        //setting current location and empty Direction List for directions
        currentLocation = "entrance_exit_gate";    //getting first item (gate item) TODO make variable for ability to change start point
        directions = new ArrayList<String>();

        for (AnimalNode animal : selectedAnimals){
            animal.updateDistance(currentLocation, ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }
        Log.d("Animal Queue Sorted", selectedAnimals.toString());

        //Getting directions to first animal   TODO replace this with method from Directions.java
        if (!selectedAnimals.isEmpty()) {
            AnimalNode currAnimal = selectedAnimals.poll();
            //setting Animal name text view
            animalToVisit = (TextView) findViewById(R.id.currentAnimaltxt);
            animalToVisit.setText(currAnimal.name);

            GraphPath<String, IdentifiedWeightedEdge> pathFound;

            if(currAnimal.group_id == null) {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
            } else {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.group_id, ZooGraphConstruct);
            }

            List<String> nodes = pathFound.getVertexList();
            Log.d("nodes: ", nodes.toString());

            int i = 1;
            DirectionStepItem temp = null;
            for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
                temp = new DirectionStepItem(ZooGraphConstruct.getEdgeWeight(e), edgeNodes.get(e.getId()).street,
                                Integer.toString(i), animalNodes.get(nodes.get(i)).name);
                directions.add(temp.toString());    //not the most elegant way to do this but it works ;-;
                Log.d("directions", temp.toString());
                i++;
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }
            Log.d("Arriving at:", currAnimal.id);

        } else {
            noSelectedAnimals = true;
            TextView noAnimalsSelected = (TextView) findViewById(R.id.no_animals_selected_txt);
            TextView headingToText = (TextView) findViewById(R.id.headingTotxt);
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
        }
        listAdapter.setDirectionItems(directions);
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;     //TODO need to change to work with GPS location
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void nextDirections(View view) {
        if (noSelectedAnimals) return;
        //TODO: need to add this logic to Directions.java to make it easier to be used in other places
        listAdapter = new ListAdapter();
        listAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        directions = new ArrayList<String>();

        if(selectedAnimals.isEmpty()){
            TextView noAnimalsSelected = (TextView) findViewById(R.id.no_animals_selected_txt);
            TextView headingToText = (TextView) findViewById(R.id.headingTotxt);
            TextView currentAnimalText = (TextView) findViewById(R.id.currentAnimaltxt);
            noAnimalsSelected.setText("All animal exhibits have been visited!");
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            currentAnimalText.setVisibility(View.INVISIBLE);
            return;
        }

        //updating distance for new closet node
        for (AnimalNode animal : selectedAnimals){
            animal.updateDistance(currentLocation, ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }

        if(!selectedAnimals.isEmpty()) {
            AnimalNode currAnimal = selectedAnimals.poll();
            animalToVisit.setText(currAnimal.name);

            GraphPath<String, IdentifiedWeightedEdge> pathFound;

            if(currAnimal.group_id == null) {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
            } else {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.group_id, ZooGraphConstruct);
            }
            List<String> nodes = pathFound.getVertexList();
            Log.d("nodes: ", nodes.toString());

            int i = 1;
            DirectionStepItem temp = null;
            for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
                temp = new DirectionStepItem(ZooGraphConstruct.getEdgeWeight(e), edgeNodes.get(e.getId()).street,
                                        Integer.toString(i), animalNodes.get(nodes.get(i)).name);
                directions.add(temp.toString());
                Log.d("Directions", temp.toString());
                i++;
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }

            Log.d("Arriving at:", currAnimal.id);
            listAdapter.setDirectionItems(directions);
        } else {
            finish();
        }
    }

    public void previousDirections(View view) {
        //TODO: Add functionality for previous button clicked
        //adds animals to an ordered list (Arraylist or queue whichever is easier) that has all the
        //animals previously visited in FILO order
    }

    public void skipDirections(View view) {
        //TODO: Add functionality for skip
        //Basically the same as next btn except does not add animal to previous animals list
    }

    public void toggleDetailed(View view) {
        if(detailed){ detailed = false; } else {detailed = true;}
        Log.d("Detailed Directions:", String.valueOf(detailed));
        //TODO some call to refresh directions
    }

    //APP NAVIGATION FUNCTIONS
    public void returnToHome(View view) {
        finish();
    }

    public void returnToRoutePlan(View view) {
        Intent intent = new Intent(this, RoutePlanActivity.class);
        startActivity(intent);
        finish();
    }
}