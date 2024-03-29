package com.example.group13zoosearch;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class AnimalList {

    static PriorityQueue<AnimalNode> selected_animal_nodes;
    static ArrayList<AnimalNode> visited_animal_nodes;
    static ArrayList<AnimalNode> selected_animal_nodes_list;
    static ArrayList<String> selected_exhibits = new ArrayList<>();
    static int currentIndex = 0;        //To be used only by directionsActivity
    static String currentLocation = "entrance_exit_gate";
    public Context context;

    //loaded data from JSON files
    private Map<String, AnimalNode> all_animal_nodes;
    private Map<String, EdgeNameItem> edges;
    private Graph<String, IdentifiedWeightedEdge> zoo_graph_info;

    public AnimalList(Context context, String nodePath, String edgePath, String graphPath){
        this.context = context;
        this.all_animal_nodes = AnimalNode.loadNodeInfoJSON(context, nodePath);;
        this.edges = EdgeNameItem.loadNodeInfoJSON(context, edgePath);
        this.zoo_graph_info = Directions.loadZooGraphJSON(context, graphPath);
        this.currentLocation = "entrance_exit_gate";        //TODO MAKE THIS WORK WITH GPS COORDS
        //Empty lists for directions later
        this.selected_animal_nodes = new PriorityQueue<AnimalNode>((node1, node2) ->
                Double.compare(node1.distance_from_location, node2.distance_from_location)
        );
        this. visited_animal_nodes = new ArrayList<AnimalNode>();
    }

    public PriorityQueue<AnimalNode> generatePriorityQueue(){
        selected_animal_nodes = new PriorityQueue<AnimalNode>((node1, node2) ->
                Double.compare(node1.distance_from_location, node2.distance_from_location)
        );

        //Finding all animalNodes corresponding to strings in specified list
        for(String str : selected_exhibits){
            selected_animal_nodes.add(all_animal_nodes.get(str));
        }

        return selected_animal_nodes;
    }

    /**
     * Generates an arrayList of animalNodes ordered by distance from the curr given Location
     **/
    public ArrayList<AnimalNode> generateArrayList(String currLoc){
        selected_animal_nodes_list = new ArrayList<AnimalNode>();

        //Finding all animalNodes corresponding to strings in specified list
        for(String str : selected_exhibits){
            AnimalNode temp = all_animal_nodes.get(str);
            temp.updateDistance(currLoc, zoo_graph_info);
            selected_animal_nodes_list.add(temp);
        }
        selected_animal_nodes_list.sort(new Comparator<AnimalNode>() {
            //Sorting via distance
            @Override
            public int compare(AnimalNode a1, AnimalNode a2) {
                return Double.compare(a1.distance_from_location, a2.distance_from_location);
            }
        });
        return selected_animal_nodes_list;
    }
    public ArrayList<AnimalNode> generateArrayList2(String currLoc){
        selected_animal_nodes_list = new ArrayList<AnimalNode>();

        //Finding all animalNodes corresponding to strings in specified list
        for (Map.Entry<String, AnimalNode> entry : DirectionsFactory.animalNodes.entrySet()) {

                AnimalNode temp =entry.getValue();
                temp.updateDistance(currLoc, zoo_graph_info);
                selected_animal_nodes_list.add(temp);

        }


        selected_animal_nodes_list.sort(new Comparator<AnimalNode>() {
            //Sorting via distance
            @Override
            public int compare(AnimalNode a1, AnimalNode a2) {
                return Double.compare(a1.distance_from_location, a2.distance_from_location);
            }
        });
        return selected_animal_nodes_list;
    }
    /*
     * Adds animal node to the front of visited animals list
     * This animal node must be an animal exhibit (not an intersection/non-exhibit node)
     */
    public void addToVisited(AnimalNode an){
        visited_animal_nodes.add(0, an);
    }

    /*
     * Resets the visited animal list
     */
    public void clearVisited(){
        visited_animal_nodes.clear();
    }

    //TODO use these for updating priority queue instead of doing it locally in Directions activity
    //adds animal to queue
    public void addAnimal(AnimalNode an){
        selected_animal_nodes.add(an);
    }

    //pops animal off queue
    public AnimalNode deleteAnimal(){
        return selected_animal_nodes.poll();
    }

    public void favoriteSelected(AnimalNode an){
        if (an.favorited == false){
            an.favorited = true;
        } else {
            an.favorited = false;
        }
    }


    //GETTERS
    public Map<String, AnimalNode> getAll_animal_nodes() {
        return all_animal_nodes;
    }

    public Map<String, EdgeNameItem> getEdges() {
        return edges;
    }

    public Graph<String, IdentifiedWeightedEdge> getZoo_graph_info() {
        return zoo_graph_info;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    //SETTERS
    //TODO make this GPS Location
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    //Update methods are called when any activities that make edits to these lists take place
    public static void updateSelected_animal_nodes(int index, String loc) {
        currentIndex = index;
        currentLocation = loc;
        Log.d("Saved Index", String.valueOf(index));
    }
}
