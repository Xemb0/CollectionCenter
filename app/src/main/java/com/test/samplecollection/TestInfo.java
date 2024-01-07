package com.test.samplecollection;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class TestInfo extends AppCompatActivity {
    String testId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_info);

        Intent intent = getIntent();
        testId = intent.getStringExtra("TestId");

        if (testId != null) {
            fetchDataFromFirebase(testId);
        } else {
            // Handle the case where testId is null
            Toast.makeText(this, "Its Null BRo", Toast.LENGTH_SHORT).show();
        }



        // Now you can use testName and testDescription to populate your UI
        // ...

        // Example: Update a TextView with the test name

    }

    @SuppressLint("RestrictedApi")
    private void fetchDataFromFirebase(String testId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Replace "id" with the actual document ID you want to fetch


        db.collection("testitems")
                .document(testId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve data from the document
                        String name = documentSnapshot.getString("NAME");
                        int price = Objects.requireNonNull(documentSnapshot.getLong("PRICE")).intValue();
                        String description = documentSnapshot.getString("DESCRIPTION");
                        int mrp = Objects.requireNonNull(documentSnapshot.getLong("MRP")).intValue();
                        int inclusions = Objects.requireNonNull(documentSnapshot.getLong("INCLUSIONS")).intValue();
                        String tag = documentSnapshot.getString("CATEGORY");

                        // Now you can use the retrieved data
                        // ...

                        // For example, create a Test objec

                        // Update your UI or perform other logic with the retrieved data

                        TextView textViewTestName = findViewById(R.id.details_test_name);
                        textViewTestName.setText(name);

                        TextView textViewTestDiscription = findViewById(R.id.details_test_description);
                        textViewTestDiscription.setText(description);

                        TextView textViewTestPrice = findViewById(R.id.details_test_price);
                        textViewTestPrice.setText(String.valueOf(price)); // Convert int to String

                        TextView textViewTestInclusions = findViewById(R.id.detials_test_inclusions);
                        textViewTestInclusions.setText(String.valueOf(inclusions)); // Convert int to String

                        // ...

                    } else {
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                });

    }
}

