package com.example.group13zoosearch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.PriorityQueue;

public class Directions {
    /**
     * Constructs Graph of animal exhibits using provided Json file
     *
     * Modeled after ZooGraph from ZooSeeker-Assets folder in CSE-110-Sprint-2022 gitHub
     * URL: https://github.com/CSE-110-Spring-2022/ZooSeeker-Assets.git
     */
    public static Graph<String, IdentifiedWeightedEdge> loadZooGraphJSON(Context context, String path) {
        // Create an empty graph to populate.
        Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);

        JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();
        importer.setVertexFactory(v -> v);
        //Setting IDs for edges
        importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);

        try{
            InputStream in = context.getAssets().open(path);
            Reader reader = new InputStreamReader(in);
            importer.importGraph(g, reader);
        }catch(IOException e) {
            e.printStackTrace();
            return g;
        }
        return g;
    }

    /**
     * Calculates the shortest path Route from start to end
     *
     * @param start String id of the starting location
     * @param end   String id of the ending location
     * @param g     Graph which contains the information
     * @return a graphPath wish nodes and edges connecting start location to end location
     */
    public static GraphPath<String,IdentifiedWeightedEdge>
    computeDirections(String start, String end, Graph<String,IdentifiedWeightedEdge> g){
        return DijkstraShortestPath.findPathBetween(g, start, end);
    }

    /**
     * utility method for computing the total distance of the path from start to end nodes in a graph
     *
     * computeDistance() has two param configurations:
     * computeDistance(shortestPathGraph, originalGraph)<--------calculates the distance given a path and the original graph
     * computeDistance(startNode, endNode, originalGraph)<--------calculates the distance given two nodes and the original graph
     */
    public static double
    computeDistance(GraphPath<String,IdentifiedWeightedEdge> path, Graph<String, IdentifiedWeightedEdge> g){
        double distance = 0.0;
        for (IdentifiedWeightedEdge e : path.getEdgeList()){
            distance += g.getEdgeWeight(e);
        }
        return distance;
    }

    public static double computeDistance(String start, String end, Graph<String, IdentifiedWeightedEdge> g) {
        double distance = 0.0;
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, end);
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            distance += g.getEdgeWeight(e);
        }
        return distance;
    }

    /**
     * calculations for someone walking 3mph (the average speed for people between the ages of 20-70)
     * Source: https://www.thehealthy.com/exercise/walking/average-walking-speed/
     */
    public static double getETA(double distance){
        return distance; //TODO IMPLEMENT THIS
    }
}
