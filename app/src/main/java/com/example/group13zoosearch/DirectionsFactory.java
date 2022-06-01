package com.example.group13zoosearch;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectionsFactory {
    public static  List<String> directionNodes;
    public static Map<String, AnimalNode> animalNodes;
    public static Map<String, EdgeNameItem> edgeNodes;
    public static Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    public static List<String> directions;
    public static LatLng currLL;
    public static String currentLocation;
    public static String previousLocation;
    public static ArrayList<AnimalNode> animalRoute;
    public static Stack<AnimalNode> visitedAnimals;
    public static int currIndex;
    public static AnimalNode currentAnimal;
    public static boolean detailed=false;
    public static boolean noSelectedAnimals;
    public static String currentLocationName;

    public static void nextDirections() {
        previousLocation = currentLocation;
        if (noSelectedAnimals) return;
        directions.clear();
        if(currIndex < 0){currIndex = 0;}else {
            while (currIndex < animalRoute.size() && animalRoute.get(currIndex).visited) {
                currIndex++;
            }
        }

        if(currIndex>= animalRoute.size()){

            Log.d("CurrIndex:", String.valueOf(currIndex));
            return;
        } else {

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

            return;
        }

        Log.d("Next Button: ","Next");
    }
    public static void generate(Context cnt)
    {
        animalNodes = AnimalNode.loadNodeInfoJSON(cnt, "exhibit_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(cnt, "trail_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(cnt, "zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());
        //Fetching selected animals and creating route plan
        AnimalList anList = new AnimalList(cnt, "exhibit_info.json","trail_info.json","zoo_graph.json");
        currentLocation = "entrance_exit_gate";
        previousLocation = currentLocation;
        animalRoute = anList.generateArrayList(currentLocation);
        animalRoute = Directions.computeRoute(currentLocation,animalRoute, ZooGraphConstruct);
        Log.d("Animal Route created:", animalRoute.toString());
        AnimalRoute.animalRoute = animalRoute;
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

            //directions = Directions.getDirectionsList(currentLocation, currAnimal, ZooGraphConstruct, edgeNodes,animalNodes);


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
        }

    }
}
