package com.example.group13zoosearch;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class AnimalList {
    PriorityQueue<Animal> selected_animals;
    PriorityQueue<Animal> all_animals;
    PriorityQueue<Animal> favorited_animals;
    ArrayList<String> exhibits = new ArrayList<>();
    static ArrayList<String> selected_exhibits = new ArrayList<>();

    public AnimalList() {
    }

    public void addToSelectAnimal(Animal an){
        //TODO: to be implemented
    }

    public void removeFromSelected(Animal an){
        //TODO: to be implemented
    }

    public void favoriteAnimal(Animal an){
        //TODO: to be implemented
    }

    public PriorityQueue<Animal> search(String str){
        //TODO: to be implemented
        return new PriorityQueue<Animal>();
    }

}