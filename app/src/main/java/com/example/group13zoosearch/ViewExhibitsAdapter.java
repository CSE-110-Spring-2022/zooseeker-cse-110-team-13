package com.example.group13zoosearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewExhibitsAdapter extends RecyclerView.Adapter<ViewExhibitsAdapter.ViewHolder>{
    private LayoutInflater mInflater;

    // creating a constructor for our variables.
    public ViewExhibitsAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewExhibitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibit_item, parent, false);

        return new ViewExhibitsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewExhibitsAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
//        String exhibit = exhibitsArrayList.get(position);
        holder.selectedExhibit.setText(AnimalList.selected_exhibits.get(position));
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return AnimalList.selected_exhibits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView selectedExhibit;
        private final View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            this.selectedExhibit = itemView.findViewById(R.id.selected_exhibit);
            this.deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                }
            });
        }
        public void removeItem(int position){
            AnimalList.selected_exhibits.remove(position);
            notifyItemRemoved(position);
        }
    }
}
