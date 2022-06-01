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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectionsActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private Map<String, AnimalNode> animalNodes;
    private Map<String, EdgeNameItem> edgeNodes;
    private Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    private List<String> directions;
    private String currentLocation;
    private String previousLocation;
    private String currentLocationName;
    private TextView animalToVisit;
    TextView noAnimalsSelected;
    TextView headingToText;
    TextView currentAnimalText;
    private boolean detailed=false;
    private boolean noSelectedAnimals;
    ListAdapter listAdapter;


    //New Directions objects :)
    private ArrayList<AnimalNode> animalRoute;
    private Stack<AnimalNode> visitedAnimals;
    private int currIndex;

    //Used in detailDirection
    private AnimalNode currentAnimal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        listAdapter = new ListAdapter();
        listAdapter.setHasStableIds(false);
        currIndex = 0;

        //recycler view
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        noAnimalsSelected = (TextView) findViewById(R.id.no_animals_selected_txt);
        headingToText = (TextView) findViewById(R.id.headingTotxt);
        currentAnimalText = (TextView) findViewById(R.id.currentAnimaltxt);

        //CREATING LISTS:
        //create AnimalNode list
        animalNodes = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(this, "trail_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(this, "zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());

        //Fetching selected animals and creating route plan
        AnimalList anList = new AnimalList(this, "exhibit_info.json","trail_info.json","zoo_graph.json");
        currentLocation = "entrance_exit_gate";
        previousLocation = currentLocation;
        animalRoute = anList.generateArrayList(currentLocation);
        animalRoute = Directions.computeRoute(currentLocation,animalRoute, ZooGraphConstruct);
        Log.d("Animal Route created:", animalRoute.toString());
        visitedAnimals = new Stack<AnimalNode>();

        //SETTING UP FIRST DIRECTION LIST
        directions = new ArrayList<String>();
        //visitedAnimals.push(animalNodes.get(currentLocation));

        //Getting directions to first animal
        if (!(animalRoute.size() == 0)) {
            while(animalRoute.get(currIndex).visited){
                currIndex++;
            }
            AnimalNode currAnimal = animalRoute.get(currIndex);
            currentAnimal = currAnimal;
            animalRoute.get(currIndex).visited = true;
            visitedAnimals.push(currAnimal);

            //setting Animal name text view
            currentAnimalText.setText(currAnimal.name);

            //directions = Directions.getDirectionsList(currentLocation, currAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            if(detailed==true){
                directions = Directions.getDirectionsList(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }
            else{
                directions = Directions.getDirectionsListBrief(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }
            Log.d("Arriving at:", currAnimal.id);
            currentLocationName = currAnimal.id;

        } else {
            noSelectedAnimals = true;
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
        }
        listAdapter.setDirectionItems(directions);
        Log.d("Current Number of Directions to arrive:", Integer.toString(listAdapter.getItemCount()));
    }

    public void nextDirections(View view) {
        previousLocation = currentLocation;
        if (noSelectedAnimals) return;
        directions.clear();
        if(currIndex < 0){currIndex = 0;}else {
            while (currIndex < animalRoute.size() && animalRoute.get(currIndex).visited) {
                currIndex++;
            }
        }

        if(currIndex>= animalRoute.size()){
            noAnimalsSelected.setText("All animal exhibits have been visited!");
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            currentAnimalText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            Log.d("CurrIndex:", String.valueOf(currIndex));
            return;
        } else {
            noAnimalsSelected.setVisibility(View.INVISIBLE);
            headingToText.setVisibility(View.VISIBLE);
            currentAnimalText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if ((currIndex< animalRoute.size() && currIndex < animalRoute.size())) {
            while(animalRoute.get(currIndex).visited){
                currIndex++;
            }
            if(currIndex>= 1) {
                visitedAnimals.push(animalRoute.get(currIndex - 1));
            }
            AnimalNode currAnimal = animalRoute.get(currIndex);
            currentAnimal = currAnimal;
            animalRoute.get(currIndex).visited = true;

            //setting Animal name text view
            currentAnimalText.setText(currAnimal.name);

            //directions = Directions.getDirectionsList(currentLocation, currAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            if(detailed==true){
                directions = Directions.getDirectionsList(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }
            else{
                directions = Directions.getDirectionsListBrief(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }
            Log.d("Arriving at:", currAnimal.id);
            Log.d("CurrIndex:", String.valueOf(currIndex));
            currentLocationName = currAnimal.id;

        } else {
            noSelectedAnimals = true;
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        }

        listAdapter.setDirectionItems(directions);
        Log.d("Next Button: ","Next");
    }

    public void previousDirections(View view) {
        previousLocation = currentLocation;
        if (noSelectedAnimals) return;

        directions.clear();

        if(visitedAnimals.empty() || currIndex <= -1){   
            noAnimalsSelected.setText("No previous Exhibits");
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            currentAnimalText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            if(currIndex > -1){
                currIndex--;
            }
            Log.d("CurrIndex:", String.valueOf(currIndex));
            return;
        } else {
            noAnimalsSelected.setVisibility(View.INVISIBLE);
            headingToText.setVisibility(View.VISIBLE);
            currentAnimalText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        if(!visitedAnimals.empty()) {
            if(currIndex >= animalRoute.size()){
                currIndex = animalRoute.size()-1;
                animalRoute.get(animalRoute.size()-1).visited = false;
            } else {animalRoute.get(currIndex).visited = false;}
            currIndex--;
            AnimalNode currAnimal = visitedAnimals.pop();
            currentAnimal = currAnimal;
            currentAnimalText.setText(currAnimal.name);

            //directions = Directions.getDirectionsList(currentLocation, currAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            if(detailed==true){
                directions = Directions.getDirectionsList(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }
            else{
                directions = Directions.getDirectionsListBrief(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }

            Log.d("Arriving at:", currAnimal.id);
            Log.d("CurrIndex:", String.valueOf(currIndex));
            currentLocationName = currAnimal.id;
            listAdapter.setDirectionItems(directions);
        } else {
            finish();
        }
        Log.d("Button: ","Previous");
    }

    public void skipDirections(View view) {
        previousLocation = currentLocation;
       //Basically the same as next btn except does not add animal to previous animals list
        if (noSelectedAnimals) return;

        directions.clear();

        while(currIndex< animalRoute.size() && animalRoute.get(currIndex).visited){
            currIndex++;
        }

        if(currIndex>= animalRoute.size()){
            noAnimalsSelected.setText("All animal exhibits have been visited!");
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            currentAnimalText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        } else {
            noAnimalsSelected.setVisibility(View.INVISIBLE);
            headingToText.setVisibility(View.VISIBLE);
            currentAnimalText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if ((currIndex< animalRoute.size() && currIndex < animalRoute.size())) {
            while(animalRoute.get(currIndex).visited){
                currIndex++;
            }
            AnimalNode currAnimal = animalRoute.get(currIndex);
            currentAnimal = currAnimal;
            animalRoute.get(currIndex).visited = true;

            //setting Animal name text view
            currentAnimalText.setText(currAnimal.name);

            //directions = Directions.getDirectionsList(currentLocation, currAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            if(detailed==true){
                directions = Directions.getDirectionsList(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }
            else{
                directions = Directions.getDirectionsListBrief(currentLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
            }

            //TODO change this to just get the user's current GPS location
            if(currAnimal.group_id == null) {
                currentLocation = currAnimal.id;
            } else {
                currentLocation = currAnimal.group_id;
            }
            Log.d("Arriving at:", currAnimal.id);
            currentLocationName = currAnimal.id;

        } else {
            noSelectedAnimals = true;
            noAnimalsSelected.setVisibility(View.VISIBLE);
            headingToText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        }

        listAdapter.setDirectionItems(directions);
        Log.d("Button: ","Skip");
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;     //TODO need to change to work with GPS location
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void toggleDetailed(View view) {
        if(detailed){ detailed = false; } else {detailed = true;}
        Log.d("Detailed Directions:", String.valueOf(detailed));
        //TODO some call to refresh directions
        directions.clear();
        if(detailed==true){
            directions = Directions.getDirectionsList(previousLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
        }
        else{
            directions = Directions.getDirectionsListBrief(previousLocation, currentAnimal, ZooGraphConstruct, edgeNodes,animalNodes);
        }
        listAdapter.setDirectionItems(directions);
        Log.d("Button: ","Toggle");
    }

    //APP NAVIGATION FUNCTIONS
    public void returnToHome(View view) {
        //AnimalList.updateSelected_animal_nodes(selectedAnimals); //TODO ADD CONFIRM HOME SCREEN...
        finish();
    }

//    public void returnToRoutePlan(View view) {
//        //AnimalList.updateSelected_animal_nodes(selectedAnimals);
//        Intent intent = new Intent(this, MapActivity.class);
//        startActivity(intent);
//        finish();
//    }
}