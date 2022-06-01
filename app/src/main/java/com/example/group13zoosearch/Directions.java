package com.example.group13zoosearch;

import android.content.Context;
import android.util.Log;

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

    public static List<String> getDirectionsList(String start, AnimalNode currAnimal, Graph<String, IdentifiedWeightedEdge> zooGraph, Map<String, EdgeNameItem> edgeNodes, Map<String, AnimalNode> animalNodes){
            GraphPath<String, IdentifiedWeightedEdge> pathFound;
            List<String> directions = new ArrayList<String>();

            if (currAnimal.group_id == null) {
                pathFound = Directions.computeDirections(start, currAnimal.id, zooGraph);
            } else {
                pathFound = Directions.computeDirections(start, currAnimal.group_id, zooGraph);
            }

            List<String> nodes = pathFound.getVertexList();
            Log.d("nodes: ", nodes.toString());

            int i = 1;
            DirectionStepItem temp = null;
            for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
                temp = new DirectionStepItem(zooGraph.getEdgeWeight(e), Objects.requireNonNull(edgeNodes.get(e.getId())).street,
                        Integer.toString(i), Objects.requireNonNull(animalNodes.get(nodes.get(i))).name, null);
                directions.add(temp.toString());    //not the most elegant way to do this but it works ;-;
                Log.d("directions", temp.toString());
                i++;
            }
            return directions;
    }

    public static List<String> getDirectionsListBrief(String start, AnimalNode currAnimal, Graph<String, IdentifiedWeightedEdge> zooGraph, Map<String, EdgeNameItem> edgeNodes, Map<String, AnimalNode> animalNodes){
        GraphPath<String, IdentifiedWeightedEdge> pathFound;
        List<String> directions = new ArrayList<String>();

        if(currAnimal.group_id == null) {
            pathFound = Directions.computeDirections(start, currAnimal.id, zooGraph);
        } else {
            pathFound = Directions.computeDirections(start, currAnimal.group_id, zooGraph);
        }

        List<String> nodes = pathFound.getVertexList();
        Log.d("nodes: ", nodes.toString());

        int i = 1;
        DirectionStepItem temp = null;
        DirectionStepItem previous = null;
        double total_distance = 0;
        String message="haha";
        List<DirectionStepItem> previousItem= new ArrayList<DirectionStepItem>();
        for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
            if(previousItem.size()==0){
                temp = new DirectionStepItem(zooGraph.getEdgeWeight(e), Objects.requireNonNull(edgeNodes.get(e.getId())).street,
                        Integer.toString(i), Objects.requireNonNull(animalNodes.get(nodes.get(i))).name,null);
            }
            else{
                temp = new DirectionStepItem(zooGraph.getEdgeWeight(e), Objects.requireNonNull(edgeNodes.get(e.getId())).street,
                        Integer.toString(i), Objects.requireNonNull(animalNodes.get(nodes.get(i))).name,previousItem.get(previousItem.size()-1));
            }

            //previous = temp;
            previousItem.add(temp);

            if(temp.getPreviousNodeItem()==null){
                total_distance+=temp.getDistance();
                continue;
            }
            if(temp.getPreviousNodeItem().getRoad().equals(temp.getRoad())){
                total_distance+=temp.getDistance();
            }
            else{

                message = Integer.toString(i)+ ". Head " + total_distance + "ft on "+ temp.getPreviousNodeItem().getRoad() + " towards " + temp.getPreviousNodeItem().getNextNode() + "\n";
                total_distance = temp.getDistance();
                directions.add(message);
                Log.d("directions",message);
                i++;
            }
        }
        if(temp==null){
            Log.d("null error","");
        }else{
        message = Integer.toString(i)+ ". Head " + total_distance + "ft on "+ temp.getRoad() + " towards " + temp.getNextNode() + "\n";
        directions.add(message);
        Log.d("directions",message);}
        return directions;
    }

    public static ArrayList<AnimalNode> computeRoute(String start, ArrayList<AnimalNode> selected_exhibits, Graph<String, IdentifiedWeightedEdge> g){
        ArrayList<AnimalNode> route = new ArrayList<AnimalNode>();
        ArrayList<AnimalNode> temp = new ArrayList<AnimalNode>();
        String currLoc = start;
        int size = selected_exhibits.size();

        for(int i = 0; i < size; i++){
            temp = sortArrayList(currLoc, selected_exhibits ,g);
            route.add(temp.get(0));
            if(temp.get(0).group_id == null){
                currLoc = temp.get(0).id;
            }else{currLoc = temp.get(0).group_id;}
            Log.d("adding:", temp.get(0).toString());
            selected_exhibits.remove(temp.get(0));
        }

        return route;
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
     * Generates an arrayList of animalNodes ordered by distance from the curr given Location
     **/
    public static ArrayList<AnimalNode> sortArrayList(String currLoc, ArrayList<AnimalNode> list, Graph<String, IdentifiedWeightedEdge> g){
        ArrayList<AnimalNode> nodeList = new ArrayList<AnimalNode>();

        //Finding all animalNodes corresponding to strings in specified list
        for(AnimalNode animal : list){
            animal.updateDistance(currLoc, g);
            nodeList.add(animal);
        }
       nodeList.sort(new Comparator<AnimalNode>() {
            //Sorting via distance
            @Override
            public int compare(AnimalNode a1, AnimalNode a2) {
                return Double.compare(a1.distance_from_location, a2.distance_from_location);
            }
        });
        return nodeList;
    }

    /**
     * calculations for someone walking 3mph (the average speed for people between the ages of 20-70)
     * Source: https://www.thehealthy.com/exercise/walking/average-walking-speed/
     */
    public static double getETA(double distance){
        return distance; //TODO IMPLEMENT THIS
    }
}
