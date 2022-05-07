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
    //vertex info
    public String id;
    public String kind;
    public String name;
    public List<String> tags;
    //edge info
    public String street;
    public String edgeId;

    public boolean visited;
    public boolean favorited;
    public int distance_from_location;
    public int ETA_time; //time in minutes


    AnimalNode(String id, String kind, String name, List<String> tags) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.tags = tags;
        this.visited = false;
        this.favorited = false;
        this.distance_from_location = 0;
        this.ETA_time = 0;
    }

    @Override
    public String toString() {
        return "AnimalNode{" +
                "id='" + id + '\'' +
                ", \n kind='" + kind + '\'' +
                ", \n name='" + name + '\'' +
                ", \n tags=" + tags +
                ", \n visited=" + visited +
                ", \n favorited=" + favorited +
                ", \n distance_from_location=" + distance_from_location +
                ", \n ETA_time=" + ETA_time +
                '}';
    }

//small note, should probably either return a priority queue OR after loading, and generating paths put them in to a priority queue
    public static List<AnimalNode> loadNodeInfoJSON(Context context, String path){
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
