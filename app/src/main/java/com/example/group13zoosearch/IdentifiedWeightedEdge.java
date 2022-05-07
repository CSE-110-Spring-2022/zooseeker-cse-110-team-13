package com.example.group13zoosearch;

import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.nio.Attribute;

/**
 * DefaultWeightedEdge with an id field which can be used
 * to look up the information about the edge name.
 *
 * Credit: Taken from the ZooSeeker-Assets folder in CSE-110-Sprint-2022 gitHub
 * URL: https://github.com/CSE-110-Spring-2022/ZooSeeker-Assets.git
 */
public class IdentifiedWeightedEdge extends DefaultWeightedEdge {
    private String id = null;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return "(" + getSource() + " :" + id + ": " + getTarget() + ")";
    }

    public static void attributeConsumer(Pair<IdentifiedWeightedEdge, String> pair, Attribute attr) {
        IdentifiedWeightedEdge edge = pair.getFirst();
        String attrName = pair.getSecond();
        String attrValue = attr.getValue();

        if (attrName.equals("id")) {
            edge.setId(attrValue);
        }
    }
}

