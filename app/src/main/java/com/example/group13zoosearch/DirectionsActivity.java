package com.example.group13zoosearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    private String currentLocation;
    private List<DirectionStepItem> directions;
    private PriorityQueue<AnimalNode> selectedAnimals;
    private List<AnimalNode> animalNodes;
    private Map<String, EdgeNameItem> edgeNodes;
    private Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    DirectionStepAdapter directionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        directionAdapter = new DirectionStepAdapter();
        directionAdapter.setHasStableIds(true);

        //recycler view
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(directionAdapter);

        //CREATING LISTS:
        //create AnimalNode list
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "sample_node_info.json");
        //Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(this, "sample_edge_info.json");
        //Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(this, "sample_zoo_graph.json");
        //Log.d("animalGraph", ZooGraphConstruct.toString());

        //Fetching selected animals
        selectedAnimals = new PriorityQueue<AnimalNode>((node1, node2) ->
                Double.compare(node1.distance_from_location, node2.distance_from_location)
        );

        //setting current location and empty Direction List for directions
        currentLocation = animalNodes.get(1).id;    //getting first item (gate item)
        directions = new ArrayList<DirectionStepItem>();

        for (AnimalNode animal : animalNodes){
            selectedAnimals.add(animal);
        }
        for (AnimalNode animal : animalNodes){
            animal.updateDistance(currentLocation, ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }

        //Getting directions to first animal
        AnimalNode currAnimal = selectedAnimals.poll();

        GraphPath<String, IdentifiedWeightedEdge> pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
        int i = 1;
        DirectionStepItem temp = null;
        for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
            temp = new DirectionStepItem(ZooGraphConstruct.getEdgeWeight(e), edgeNodes.get(e.getId()).street, Integer.toString(i));
            directions.add(temp);
            Log.d("directions", i + ". " + temp.toString());
            i++;
        }
        currentLocation = currAnimal.id;
        Log.d("currentLoc", currAnimal.id);

        directionAdapter.setDirectionItems(directions);
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void nextDirections(View view) {
        directionAdapter = new DirectionStepAdapter();
        directionAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(directionAdapter);

        directions = new ArrayList<DirectionStepItem>();
        AnimalNode currAnimal = selectedAnimals.poll();
        GraphPath<String, IdentifiedWeightedEdge> pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
        int i = 1;

        DirectionStepItem temp = null;
//        List<IdentifiedWeightedEdge> test = pathFound.getEdgeList();
//        for( int i = 0; i< test.size(); i++){
//            temp = new DirectionStepItem(ZooGraphConstruct.getEdgeWeight(e), edgeNodes.get(e.getId()).street, Integer.toString(i));
//        }
        for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
            temp = new DirectionStepItem(ZooGraphConstruct.getEdgeWeight(e), edgeNodes.get(e.getId()).street, Integer.toString(i));
            directions.add(temp);
            Log.d("directions", temp.toString());
            Log.d("Directions", directions.toString());
            i++;
        }
        currentLocation = currAnimal.id;
        Log.d("currentLoc", currAnimal.id);
        Log.d("Directions", directions.toString());
        directionAdapter.setDirectionItems(directions);
    }

    public void returnToHome(View view) {
        //TODO Make sure this closes everything correctly and updates SelectedAnimalList
        finish();
    }
}