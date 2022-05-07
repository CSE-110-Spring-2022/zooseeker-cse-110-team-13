package com.example.group13zoosearch;


import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    public AnimalList animalList = new AnimalList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.search_recycler);

        buildRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //called when the user presses enter
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            //called while the text is changing
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<String> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (String item : animalList.exhibits) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchAdapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {
//        myDao todoListItemDao = ZooDataBase.getSingleton(this).myDao();
//        Map<String, ZooData.VertexInfo> zooItems = todoListItemDao.getAll();
        Map<String,ZooData.VertexInfo>  zooitem = ZooData.loadVertexInfoJSON(this, "sample_node_info.json");

        for (Map.Entry<String,ZooData.VertexInfo> entry : zooitem.entrySet())
            animalList.exhibits.add(entry.getValue().name);


        // initializing our adapter class.
        searchAdapter = new SearchAdapter(animalList.exhibits, SearchActivity.this);
//        searchAdapter.setHasStableIDs(true);
        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        recyclerView.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        recyclerView.setAdapter(searchAdapter);

    }


    public void OnGoBackClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

}