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
        selected_exhibits.add("xijiyang");    //kbell: what is this for?
    }

    public void addToSelectAnimal(Animal an){
        //TODO: to be implemented
    }

    public void removeNode(List<AnimalNode> list, AnimalNode an){
        list.remove(an); //TODO: probably needs to be edited to work with priority queue
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
