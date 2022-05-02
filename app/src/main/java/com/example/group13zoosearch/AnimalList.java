package com.example.group13zoosearch;

import java.util.List;
import java.util.PriorityQueue;

public class AnimalList {
    public void addNode(List<AnimalNode> list, AnimalNode an){
        list.add(an);
    }

    public void removeNode(List<AnimalNode> list, AnimalNode an){
        list.remove(an);
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
