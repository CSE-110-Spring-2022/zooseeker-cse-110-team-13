package com.example.group13zoosearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewExhibitsActivity extends AppCompatActivity {

    private ViewExhibitsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exhibits);
//        AnimalList animalList = new AnimalList();
//
//        ListView listView = findViewById(R.id.exhibit_list);
//        adapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                animalList.selected_exhibits);
//        listView.setAdapter(adapter);
        recyclerView = findViewById(R.id.recycler_exhibit_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.

        adapter = new ViewExhibitsAdapter(this);
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    public void OnGoBackClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }
}