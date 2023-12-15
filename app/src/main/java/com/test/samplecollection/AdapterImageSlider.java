package com.test.samplecollection;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

 class AdapterImageSlider extends RecyclerView.Adapter<AdapterImageSlider.HorizontalHolder> {
    ArrayList<Integer> image;

    public AdapterImageSlider(ArrayList<Integer> image) {
        this.image = image;
    }

    @NonNull
    @Override
    public AdapterImageSlider.HorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, parent, false);
        return new HorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalHolder horizontallAdaptor, int position) {
        horizontallAdaptor.horizontalImage.setImageResource(image.get(position));
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class HorizontalHolder extends RecyclerView.ViewHolder {
        ImageView horizontalImage;

        public HorizontalHolder(@NonNull View itemView) {
            super(itemView);
            horizontalImage = itemView.findViewById(R.id.horizontal_image);
        }
    }
}


