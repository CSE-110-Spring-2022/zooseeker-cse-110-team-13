package com.example.group13zoosearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<String> directionItems = Collections.emptyList();

    public void setDirectionItems(List<String> newDirectionItems){
        this.directionItems.clear();
        this.directionItems = newDirectionItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.direction_step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setString(directionItems.get(position));
    }

    @Override
    public int getItemCount() {
        return directionItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameText;
        private String directionholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameText = itemView.findViewById(R.id.direction_txt);
        }

        //Getters
        public String getString() {
            return directionholder;
        }


        public void setString(String direction) {
            this.directionholder = direction;
            this.nameText.setText(direction);
        }
    }

}
