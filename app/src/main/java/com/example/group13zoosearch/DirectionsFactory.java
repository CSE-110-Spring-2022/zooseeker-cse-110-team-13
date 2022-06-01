package com.example.group13zoosearch;

import org.jgrapht.Graph;

import java.util.List;
import java.util.Map;

public class DirectionsFactory {
    private Map<String, AnimalNode> animalNodes;
    private Map<String, EdgeNameItem> edgeNodes;
    private Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct;
    private List<String> directions;
    private String currentLocation;
}
