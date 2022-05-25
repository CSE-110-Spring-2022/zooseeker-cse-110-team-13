package com.example.group13zoosearch;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class AnimalList {
    PriorityQueue<AnimalNode> selected_animal_nodes;
    Map<String, AnimalNode> all_exhibits;
    PriorityQueue<AnimalNode> favorited_animals;
    static ArrayList<String> selected_exhibits;
    public Context context;

    public AnimalList(Context context, Map<String, AnimalNode> all_exhibits){
        this.context = context;
        this.all_exhibits = all_exhibits;
    }

    public PriorityQueue<AnimalNode> generatePriorityQueue(){
        selected_animal_nodes = new PriorityQueue<AnimalNode>((node1, node2) ->
                Double.compare(node1.distance_from_location, node2.distance_from_location)
        );

        //Finding all animalNodes corresponding to strings in specified list
        for(String str : selected_exhibits){
            selected_animal_nodes.add(all_exhibits.get(str));
        }

        return selected_animal_nodes;
    }

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
        //TODO: to be implemented
        return new PriorityQueue<AnimalNode>();
    }

}
