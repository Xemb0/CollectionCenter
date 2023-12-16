package com.test.samplecollection;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
        LinearLayout loginbtn = view.findViewById(R.id.btn_login);

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
        LinearLayout logoutbtn = view.findViewById(R.id.btn_login);
        TextView logoutbtnText = view.findViewById(R.id.login_button_text);

        // Load image using Glide with center crop
        Glide.with(this)
                .load(photoUrl)
                .centerCrop()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        // Apply circular outline to the loaded image
                        ivImage.setImageDrawable(resource); // Set the loaded image
                        applyCircularOutline(ivImage); // Apply circular outline
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Handle placeholder if needed
                    }
                });

        TextView name = view.findViewById(R.id.tvName);
        name.setText(displayName);
        logoutbtnText.setText("Logout");
    }

    // Method to apply circular outline to an ImageView
    private void applyCircularOutline(ImageView imageView) {
        if (imageView != null) {
            // Apply circular outline using a custom shape drawable
            int strokeWidth = 2; // Adjust the stroke width as needed
            int strokeColor = Color.parseColor("#673AB7"); // Replace with desired outline color

            GradientDrawable strokeDrawable = new GradientDrawable();
            strokeDrawable.setShape(GradientDrawable.OVAL);
            strokeDrawable.setStroke(strokeWidth, strokeColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setClipToOutline(true);
                imageView.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        int width = view.getWidth();
                        int height = view.getHeight();
                        outline.setOval(0, 0, width, height);
                    }
                });
            } else {
                // For devices below Lollipop, set background drawable to create the outline
                imageView.setBackground(strokeDrawable);
            }
        }
    }


    // Method to navigate to LoginActivity
    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        requireContext().startActivity(intent);
       // Finish the current activity after navigating to login
    }
}
