package com.example.group13zoosearch;


public class AnimalNode {
    String name;
    String category;
    boolean visited;
    boolean favorited;
    int distance_from_location;
    int[] ETA_time;
    //needed for graph traversal
    AnimalNode parentNode;
    int neighborCount;
    //whatever else needs to go here to be added

    AnimalNode(){
        name = "";
        category = "";
        parentNode = null;
        visited = false;
        favorited = false;
        distance_from_location = 0;
        neighborCount = 0;
        ETA_time = new int[]{0,0};
    }

    AnimalNode(String n, String c){
        name = n;
        category = c;
        parentNode = null;
        visited = false;
        favorited = false;
        distance_from_location = 0;
        neighborCount = 0;
        ETA_time = new int[]{0,0};
    }

    boolean isVisited(){
        return visited;
    }

    boolean isFavorited(){
        return favorited;
    }

}
