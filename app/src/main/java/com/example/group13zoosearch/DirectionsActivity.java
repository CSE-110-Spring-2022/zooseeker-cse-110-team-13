package com.example.group13zoosearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        AnimalNodeAdapter animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.animal_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);

//========================need to add proper tests to this but for now this is my testing ====================================================
        //create AnimalNode list (this will be done somewhere else eventually)
        List<AnimalNode> animalNodes = AnimalNode.loadNodeInfoJSON(this, "sample_node_info.json");
        Log.d("Animal List", animalNodes.toString());

        //now can create a list of what the edges are called (this will be done somewhere else eventually)
        Map<String, EdgeNameItem> edgeNodes = EdgeNameItem.loadNodeInfoJSON(this, "sample_edge_info.json");
        Log.d("Edges", edgeNodes.toString());

        //Creating graph (this will be done somewhere else eventually)
        Graph<String, IdentifiedWeightedEdge> ZooGraphConstruct = Directions.loadZooGraphJSON(this, "sample_zoo_graph.json");
        Log.d("animalGraph", ZooGraphConstruct.toString());

        for (AnimalNode animal : animalNodes){
            animal.updateDistance("entrance_exit_gate", ZooGraphConstruct);
            animal.ETA_time = Directions.getETA(animal.distance_from_location);
        }
        GraphPath<String, IdentifiedWeightedEdge> pathFound = Directions.computeDirections("arctic_foxes", "lions", ZooGraphConstruct);
        int i = 1;
        for (IdentifiedWeightedEdge e : pathFound.getEdgeList()) {
            System.out.printf("  %d. Walk %.0f meters along %s from THIS PLACE to THAT PLACE.\n",
                    i,
                    ZooGraphConstruct.getEdgeWeight(e),
                    edgeNodes.get(e.getId()).street);
//                    test.get(test2.getEdgeSource(e).toString()).name,
//                    test.get(test2.getEdgeTarget(e).toString()).name);
            i++;
        }
        animalAdapter.setAnimalNodeList(animalNodes);
        System.out.println("distance =" + Directions.computeDistance(pathFound, ZooGraphConstruct));
    }

    public void nextDirections(View view) {
        //some code for updating the direction recycler
    }

    public void returnToHome(View view) {
        //TODO Make sure this closes everything correctly and updates SelectedAnimalList
        finish();
    }
}