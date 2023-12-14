package com.test.samplecollection;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AdapterTag extends RecyclerView.Adapter<AdapterTag.TagViewHolder> {

    private HashSet<String> tagSet;
    private HashSet<String> selectedTags = new HashSet<>();
    private TagClickListener tagClickListener;

    public void setTagClickListener(TagClickListener listener) {
        this.tagClickListener = listener;
    }

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

        // Check if the tag is in the selectedTagList to determine its state
        if (selectedTags.contains(currentTag)) {
            holder.button.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.my_tag_select));
        } else {
            holder.button.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.my_tag_deselect));
        }

        holder.button.setOnClickListener(v -> {
            // Toggle tag selection on click
            if (selectedTags.contains(currentTag)) {
                selectedTags.remove(currentTag); // Deselect tag
                holder.button.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.my_tag_deselect));
            } else {
                selectedTags.add(currentTag); // Select tag
                holder.button.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.my_tag_select));
            }
            // Notify the listener about tag selection change
            if (tagClickListener != null) {
                tagClickListener.onTagClicked(currentTag);
            }
        });
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

    public interface TagClickListener {
        void onTagClicked(String tag);
    }

}
