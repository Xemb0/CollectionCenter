package com.test.samplecollection;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.test.samplecollection.TestInfo;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    ArrayList<Test> testsList;
    private static final String TAG = "SearchFragment";

    AdapterForTestItem adapterForTestItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_list_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testsList = new ArrayList<>();
        adapterForTestItem = new AdapterForTestItem(testsList);

        recyclerView.setAdapter(adapterForTestItem);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("testitems")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot document : list) {
                        String name = document.getString("Name");
                        double price = document.getDouble("PRICE");
                        String description = document.getString("DESCRIPTION");
                        double mrp = document.getDouble("MRP");
                        String inclusions = document.getString("INCLUSIONS");

                        Test test = new Test(name, price, description, mrp, inclusions);
                        testsList.add(test);

                        // Log retrieved data for verification
                        Log.d(TAG, "Test Name: " + name + ", Price: " + price + ", Description: " + description + ", MRP: " + mrp + ", Inclusions: " + inclusions);
                    }

                    // Set RecyclerView adapter with the retrieved data
                    adapterForTestItem.notifyDataSetChanged();

                })
                .addOnFailureListener(e -> {
                    // Log failure message
                    Log.e(TAG, "Error getting documents: ", e);
                });


        return view;
    }
}

