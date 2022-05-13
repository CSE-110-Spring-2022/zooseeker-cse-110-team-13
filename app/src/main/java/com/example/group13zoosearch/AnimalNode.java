package com.example.group13zoosearch;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.Graph;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AnimalNode {
    //Public fields, may choose to make some of these private in the future
    //vertex info
    public String id;
    public String kind;
    public String name;
    public List<String> tags;

    public boolean visited;
    public boolean favorited;
    public double distance_from_location;
    public double ETA_time; //time in minutes


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
                ", distance=" + distance_from_location +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalNode)) return false;
        AnimalNode that = (AnimalNode) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //small note, should probably either return a priority queue OR after loading, and generating paths put them in to a priority queue
    public static Map<String, AnimalNode> loadNodeInfoJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<AnimalNode>>(){}.getType();
            List<AnimalNode> data = gson.fromJson(reader, type);

            Map<String, AnimalNode> indexedNodeData = data
                    .stream()
                    .collect(Collectors.toMap(v -> v.id, datum -> datum));

            return indexedNodeData;

        } catch (IOException e){
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    //Updates the distance of current node given a starting node
    public void updateDistance(String start, Graph<String, IdentifiedWeightedEdge> graph){
        this.distance_from_location = Directions.computeDistance(start, this.id, graph);
    }

}
