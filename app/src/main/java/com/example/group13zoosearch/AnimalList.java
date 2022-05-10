package com.example.group13zoosearch;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AnimalList {
    PriorityQueue<AnimalNode> selected_animals;
    PriorityQueue<AnimalNode> all_animals;
    PriorityQueue<AnimalNode> favorited_animals;
    ArrayList<String> exhibits = new ArrayList<>();
    static ArrayList<String> selected_exhibits = new ArrayList<>();

    public AnimalList() {
    }

    public void addToSelectAnimal(AnimalNode an){
        //TODO: to be implemented
    }

    public void removeFromSelected(AnimalNode an){
        //TODO: to be implemented
    }

    public void favoriteAnimal(AnimalNode an){
        //TODO: to be implemented
    }

    public PriorityQueue<AnimalNode> search(String str){
        //TODO: to be implemented
        return new PriorityQueue<AnimalNode>();
    }

}
