package com.example.group13zoosearch;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;

public class AnimalList {
    PriorityQueue<AnimalNode> selected_animals;
    PriorityQueue<AnimalNode> all_animals;
    PriorityQueue<AnimalNode> favorited_animals;
    ArrayList<String> exhibits = new ArrayList<>();
    static ArrayList<String> selected_exhibits = new ArrayList<>();

    public AnimalList() {
        selected_exhibits.add("test");    
    }

    public void addToSelectAnimal(AnimalNode an){
        //TODO: to be implemented
        selected_animals.add(an);
    }
    public AnimalNode getFirstValue()
    {
        return selected_animals.peek();
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
