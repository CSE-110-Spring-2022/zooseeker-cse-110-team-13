package com.example.group13zoosearch;

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

public class DirectionsActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private PriorityQueue<AnimalNode> selectedAnimals;
    private Map<String, AnimalNode> animalNodes;
    private Map<String, EdgeNameItem> edgeNodes;
    private Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    private List<String> directions;
    private String currentLocation;
    private TextView animalToVisit;
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
        //create AnimalNode list
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "sample_node_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(this, "sample_edge_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(this, "sample_zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());

        //Fetching selected animals
        AnimalList anList = new AnimalList(this, animalNodes);
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

        //Getting directions to first animal
        if (!selectedAnimals.isEmpty()) {
            AnimalNode currAnimal = selectedAnimals.poll();
            //setting Animal name text view
            animalToVisit = (TextView) findViewById(R.id.currentAnimaltxt);
            animalToVisit.setText(currAnimal.name);

            GraphPath<String, IdentifiedWeightedEdge> pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
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
            currentLocation = currAnimal.id;
            Log.d("Arriving at:", currAnimal.id);
        } else {
            TextView noAnimalsSelected = (TextView) findViewById(R.id.no_animals_selected_txt);
            TextView headingToText = (TextView) findViewById(R.id.headingTotxt);
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
        }
        listAdapter.setDirectionItems(directions);
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void nextDirections(View view) {
        listAdapter = new ListAdapter();
        listAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        directions = new ArrayList<String>();

        //updating distance for new closet node
        for (AnimalNode animal : selectedAnimals){
            animal.updateDistance(currentLocation, ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }

        if(!selectedAnimals.isEmpty()) {
            AnimalNode currAnimal = selectedAnimals.poll();
            animalToVisit.setText(currAnimal.name);
            GraphPath<String, IdentifiedWeightedEdge> pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
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
            currentLocation = currAnimal.id;
            Log.d("Arriving at:", currAnimal.id);
            listAdapter.setDirectionItems(directions);
        } else {
            finish();
        }
    }

    public void returnToHome(View view) {
        finish();
    }
}