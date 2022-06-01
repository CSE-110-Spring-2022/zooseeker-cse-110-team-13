package com.example.group13zoosearch;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
//    public AnimalList animalList = new AnimalList();
    public ArrayList<String> animalList = new ArrayList<>();
    public ArrayList<AnimalNode> myList = new ArrayList<AnimalNode>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        myDao mydao = ZooDataBase.getSingleton(this).myDao();
//        List<ZooData> zooDataItems = mydao.getAll();
//        for(ZooData name : zooDataItems) {
//            animalList.add(name.name);
//        }
//        searchAdapter = new SearchAdapter(animalList);
//        searchAdapter.setHasStableIds(true);
//
//
        recyclerView = findViewById(R.id.search_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(searchAdapter);

        buildRecyclerView();

//        final Button addButton = findViewById(R.id.add_button);
        TextView textView = (TextView) findViewById(R.id.exhibit);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String selected_exhibit = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.exhibit)).getText().toString();
                if(!AnimalList.selected_exhibits.contains(selected_exhibit))
                    AnimalList.selected_exhibits.add(selected_exhibit);

                Toast.makeText(getApplicationContext(), selected_exhibit + " added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));
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

    /**
     * Search result filter
     *
     * Modified from online sources
     * URL: https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
     */
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<String> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
//        for (String item : animalList) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredList.add(item);
//            }
//        }
        for (AnimalNode a: myList) {
            if (a.kind.equals("exhibit")) {
                if((a.id.toLowerCase().contains(text.toLowerCase()))||(a.tags.contains(text.toLowerCase()))){
                    filteredList.add(a.id);
                }
            }
        }

        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchAdapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {
//        myDao todoListItemDao = ZooDataBase.getSingleton(this).myDao();
//        Map<String, ZooData.VertexInfo> zooItems = todoListItemDao.getAll();
        Map<String, AnimalNode> zooitems = AnimalNode.loadNodeInfoJSON(this, "exhibit_info.json");
        String[] animalIds = zooitems.keySet().toArray(new String[0]);


        for(String id: animalIds){
            myList.add(zooitems.get(id));
        }

        for (Map.Entry<String, AnimalNode> entry : zooitems.entrySet()) {
            if (entry.getValue().kind.equals("exhibit"))
                animalList.add(entry.getValue().id);
        }
//        animalList.addAll(Arrays.asList(animalIds));

        // initializing our adapter class.
        searchAdapter = new SearchAdapter(animalList, SearchActivity.this);
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
        View view2 = this.getCurrentFocus();
        if (view2 != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

}