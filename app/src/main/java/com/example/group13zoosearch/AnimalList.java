package com.example.group13zoosearch;

import android.content.Context;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class AnimalList {
    PriorityQueue<AnimalNode> selected_animal_nodes;
    ArrayList<AnimalNode> visited_animal_nodes;
    static ArrayList<String> selected_exhibits = new ArrayList<>();
    public Context context;

    //loaded data from JSON files
    private Map<String, AnimalNode> all_animal_nodes;
    private Map<String, EdgeNameItem> edges;
    private Graph<String, IdentifiedWeightedEdge> zoo_graph_info;

    //not currently using
    PriorityQueue<AnimalNode> favorited_animals;

    public AnimalList(Context context, String nodePath, String edgePath, String graphPath){
        this.context = context;
        this.all_animal_nodes = AnimalNode.loadNodeInfoJSON(context, nodePath);;
        this.edges = EdgeNameItem.loadNodeInfoJSON(context, edgePath);
        this.zoo_graph_info = Directions.loadZooGraphJSON(context, graphPath);

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

    public PriorityQueue<AnimalNode> search(String str){
        //TODO: to be  [THIS CAN BE TAKEN OUT?]
        return new PriorityQueue<AnimalNode>();
    }

}
