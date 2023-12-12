package com.test.samplecollection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.samplecollection.R;

import java.util.ArrayList;
import java.util.Random;

public class UserFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            // Load user's photo into an ImageView in the fragment's layout
            String userDisplayName = firebaseUser.getDisplayName();
            loadUserPhoto(view, firebaseUser.getPhotoUrl(),userDisplayName);

        }

        // Find the ListView in your fragment_user layout
        ListView listView = view.findViewById(R.id.listView);

        // Generate random items for the ListView
        ArrayList<String> itemList = generateRandomItems();

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        return view;
    }

    // Method to generate random items
    private ArrayList<String> generateRandomItems() {
        ArrayList<String> items = new ArrayList<>();
        Random random = new Random();

        // Generate 10 random items for demonstration
        for (int i = 0; i < 10; i++) {
            items.add("User Item " + (i + 1));
        }

        return items;
    }

    // Method to load user's photo into ImageView
    private void loadUserPhoto(View view, Uri photoUrl, String displayName) {
        ImageView ivImage = view.findViewById(R.id.ivProfilePhoto); // Replace with your ImageView ID
        Glide.with(this)
                .load(photoUrl)
                .circleCrop() // Apply circular crop transformation
                .into(ivImage);
        TextView name = view.findViewById(R.id.tvName);
        name.setText(displayName);

    }

    // Add this method to handle the user's login event
    public void handleUserLogin() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            // Load user's photo after successful login
            loadUserPhoto(getView(), firebaseUser.getPhotoUrl(),firebaseUser.getDisplayName());
        }
    }
}
