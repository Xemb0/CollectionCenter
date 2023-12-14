package com.test.samplecollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AdapterTag extends RecyclerView.Adapter<AdapterTag.TagViewHolder> {

    private HashSet<String> tagSet;

    public AdapterTag(HashSet<String> tagSet) {
        this.tagSet = tagSet;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        List<String> tagList = new ArrayList<>(tagSet);
        String currentTag = tagList.get(position);
        holder.setButtonText(currentTag);
    }

    @Override
    public int getItemCount() {
        return tagSet.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_tag);
        }

        public void setButtonText(String text) {
            button.setText(text);
        }
    }
}
