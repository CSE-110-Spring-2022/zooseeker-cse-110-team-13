package com.example.group13zoosearch;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class AnimalNode {
    //Public fields, may choose to make some of these private in the future
    public String name;
    public String category;
    public boolean visited;
    public boolean favorited;
    public int distance_from_location;
    public int ETA_time; //time in minutes
    //needed for graph traversal
    public AnimalNode parentNode;
    public int neighborCount;
    //whatever else needs to go here to be added

    AnimalNode(String name, String category) {
        this.name = name;
        this.category = category;
        this.parentNode = null;
        this.visited = false;
        this.favorited = false;
        this.distance_from_location = 0;
        this.neighborCount = 0;
        this.ETA_time = 0;
    }

    @Override
    public String toString() {
        return "AnimalNode{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", visited=" + visited +
                ", favorited=" + favorited +
                ", distance_from_location=" + distance_from_location +
                ", ETA_time=" + ETA_time +
                ", neighborCount=" + neighborCount +
                '}';
    }

    public static List<AnimalNode> loadJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<AnimalNode>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
