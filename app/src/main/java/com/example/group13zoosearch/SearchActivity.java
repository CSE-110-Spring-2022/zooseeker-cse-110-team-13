package com.example.group13zoosearch;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AnimalNodeAdapter animalAdapter = new AnimalNodeAdapter();
        animalAdapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.animal_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);

        //need to add proper tests to this but for now this is my testing
        List<AnimalNode> test = AnimalNode.loadNodeInfoJSON(this, "sample_node_info.json");
        String[] aa = {"bird","other tags"};
        test.add(new AnimalNode("Bird", "exibit", "Bird", Arrays.asList(aa)));
        animalAdapter.setAnimalNodeList(test);
        Log.d("ANIMAL LIST ACTIVITY", test.toString());

        //now can create a list of what the edges are called
        List<EdgeNameItem> test1 = EdgeNameItem.loadNodeInfoJSON(this, "sample_edge_info.json");
        Log.d("Edges", test1.toString());
    }
}