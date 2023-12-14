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
import com.test.samplecollection.kotlin.LoginActivity;

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
        TextView loginbtn = view.findViewById(R.id.btnLogout);

        if (firebaseUser != null) {
            String userDisplayName = firebaseUser.getDisplayName();
            loadUserPhoto(view, firebaseUser.getPhotoUrl(), userDisplayName);

            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sign out the user
                    firebaseAuth.signOut();
                    Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                    navigateToLogin();
                }
            });
        } else {
            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToLogin();
                }
            });
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
        TextView logoutbtn = view.findViewById(R.id.btnLogout);
        Glide.with(this)
                .load(photoUrl)
                .circleCrop() // Apply circular crop transformation
                .into(ivImage);
        TextView name = view.findViewById(R.id.tvName);
        name.setText(displayName);
        logoutbtn.setText("Logout");
    }

    // Method to navigate to LoginActivity
    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        requireContext().startActivity(intent);
       // Finish the current activity after navigating to login
    }
}
