package com.example.group13zoosearch;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DirectionsBuilder {
    public List<Pair<String,LatLng>> positions; //We will have a list of Names an LatLng
    List<String> nodes;
    AnimalList anList;
    PriorityQueue<AnimalNode> selectedAnimals;
    Map<String, AnimalNode> animalNodes;

    private double distance(LatLng l1, LatLng l2) {
        double theta = l1.longitude - l2.longitude;
        double dist = Math.sin(deg2rad(l1.latitude))
                * Math.sin(deg2rad(l2.latitude))
                + Math.cos(deg2rad(l1.latitude))
                * Math.cos(deg2rad(l2.latitude))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //update location when start and when position moves.
    public void updateLocations(LatLng currentPosition, Context op)
    {
        if(selectedAnimals.isEmpty())return;
        if(nodes.isEmpty()) return;
        for(int i = 0; i < nodes.size();i++)
        {
            AnimalNode n = animalNodes.get(nodes.get(i));
            if(n.lng!= null)
            {
                LatLng pos = new LatLng(n.lat,n.lng);
                if(distance(currentPosition,pos)<=100)
                {
                    Log.d("Removing node due to closeness",n.name);
                    int j = 0;
                    while(j<=i)
                    {
                        nodes.remove(j);
                        if(nodes.isEmpty())
                        {
                            generateDirections(currentPosition,op);

                        }
                    }
                }
            }

        }

    }

    //call when on start and when the plan has first been calculated/modified.
    public void generateDirections(LatLng start,Context op)
    {
        List<String> directions;
        PriorityQueue<AnimalNode> selectedAnimals;
        Queue<AnimalNode> visitedAnimals;
        Map<String, EdgeNameItem> edgeNodes;
        Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
        String currentLocation;
        TextView animalToVisit;
        boolean detailed;
        boolean noSelectedAnimals;
        //CREATING LISTS:
        //create AnimalNode list
        animalNodes = AnimalNode.loadNodeInfoJSON(op, "exhibit_info.json");

        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(op, "trail_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(op, "zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());

        //Fetching selected animals
        anList = new AnimalList(op, "exhibit_info.json","trail_info.json","zoo_graph.json");
        selectedAnimals = anList.generatePriorityQueue();
        Log.d("Animal Queue created", selectedAnimals.toString());

        //SETTING UP FIRST DIRECTION LIST
        //setting current location and empty Direction List for directions
        currentLocation = "entrance_exit_gate";    //getting first item (gate item) TODO make variable for ability to change start point
        directions = new ArrayList<String>();
        double smallest = Double.MAX_VALUE;
        AnimalNode currClosest = animalNodes.get("entrance_exit_gate"); //we will find the closest path to the currentlocation
        Log.d("Values", currClosest.id);
        Map.Entry<String, Double> min = null;
        for (Map.Entry<String, AnimalNode> entry : animalNodes.entrySet()) {
            try {
                Log.d("Lat", entry.getValue().lat.toString());
                LatLng ln = new LatLng(entry.getValue().lat, entry.getValue().lng);
                if (currClosest == null || smallest > distance(start,ln)) {
                    currClosest = entry.getValue();
                    smallest = distance(start,ln);
                }
            } catch (Exception e) {
                System.out.println("Oops!");
            }

        }
        currentLocation = currClosest.id;
        for (AnimalNode animal : selectedAnimals){
            animal.updateDistance(currentLocation, ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }
        Log.d("Animal Queue Sorted", selectedAnimals.toString());

        //Getting directions to first animal
        if (!selectedAnimals.isEmpty()) {
            AnimalNode currAnimal = selectedAnimals.poll();
            //setting Animal name text view
            GraphPath<String, IdentifiedWeightedEdge> pathFound;

            if(currAnimal.group_id == null) {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.id, ZooGraphConstruct);
            } else {
                pathFound = Directions.computeDirections(currentLocation, currAnimal.group_id, ZooGraphConstruct);
            }

            nodes = pathFound.getVertexList();
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

        }
    }
}
