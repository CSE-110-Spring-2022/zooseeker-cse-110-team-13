package com.example.group13zoosearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class AnimalNodeAdapter extends RecyclerView.Adapter<AnimalNodeAdapter.ViewHolder>{
    private List<AnimalNode> animalNodeList = Collections.emptyList();

    public void setAnimalNodeList(List<AnimalNode> newAnimalNodes){
        this.animalNodeList.clear();
        this.animalNodeList = newAnimalNodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.animal_node_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setAnimalNode(animalNodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return animalNodeList.size();
    }

//    @Override
//    public long getItemId(int position){
//        return animalNodeList.get(position).name;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private TextView distanceText;
        private AnimalNode animalNode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameText = itemView.findViewById(R.id.animal_name_txt);
            this.distanceText = itemView.findViewById(R.id.distance_to_animal_txt);
        }

        //Getters
        public AnimalNode getAnimalNode() {
            return animalNode;
        }

        //Setters
        public void setDistanceText(TextView distanceText) {
            String dist = Double.toString(animalNode.distance_from_location);
            this.distanceText.setText(dist);
        }

        public void setAnimalNode(AnimalNode animalNode) {
            String dist = Double.toString(animalNode.distance_from_location);

            this.animalNode = animalNode;
            this.nameText.setText(animalNode.name);
            this.distanceText.setText(dist + " ft");
        }
    }
}
