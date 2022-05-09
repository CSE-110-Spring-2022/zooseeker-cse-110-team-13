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
        recyclerView = findViewById(R.id.recycler_exhibit_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new ViewExhibitsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    public void OnGoBackClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }
}