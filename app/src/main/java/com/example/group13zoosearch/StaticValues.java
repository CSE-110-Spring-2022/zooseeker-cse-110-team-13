package com.example.group13zoosearch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//Create using StaticValues.generate(Context cnt);
public class StaticValues {
    public static List<String> nodes;
    public static LatLng currLocation;
    public static Map<String, AnimalNode> animalNodes; //give string get node
    public static Map<String, EdgeNameItem> edgeNodes; // give string get edgeName Item
    public static Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    public static void generate(Context cnt)
    {
        animalNodes = AnimalNode.loadNodeInfoJSON(cnt, "exhibit_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called
        edgeNodes = EdgeNameItem.loadNodeInfoJSON(cnt, "trail_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph
        ZooGraphConstruct = Directions.loadZooGraphJSON(cnt, "zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());
    }

}
