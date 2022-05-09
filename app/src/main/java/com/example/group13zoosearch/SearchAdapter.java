package com.example.group13zoosearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    // creating a variable for array list and context.
    private ArrayList<String> animalArrayList;
    private Context context;

    // creating a constructor for our variables.
    public SearchAdapter(ArrayList<String> animalArrayList, Context context) {
        this.animalArrayList = animalArrayList;
        this.context = context;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<String> filteredList) {
        // below line is to add our filtered
        // list in our course array list.
        animalArrayList = filteredList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        String animals = animalArrayList.get(position);
        holder.animal.setText(animals);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return animalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView animal;
        private final View addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            animal = itemView.findViewById(R.id.exhibit);
            addButton = itemView.findViewById(R.id.add_button);

//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addItem(animal.getText().toString());
//                }
//            });
        }

//        public void addItem(String item){
//            AnimalList.selected_exhibits.add(item);
//            notifyItemInserted(AnimalList.selected_exhibits.size()-1);
//        }
    }
}
