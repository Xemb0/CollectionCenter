package com.test.samplecollection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements AdapterTag.TagClickListener{

    ArrayList<Test> testsList;
    HashSet<String> tagList;
    HashSet<String> selectedTagList;

    androidx.appcompat.widget.SearchView searchView;
    private static final String TAG = "SearchFragment";

    AdapterForTestItem adapterForTestItem;
    AdapterTag adapterTag;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_list_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testsList = new ArrayList<>();
        adapterForTestItem = new AdapterForTestItem(testsList);
        recyclerView.setAdapter(adapterForTestItem);


        selectedTagList = new HashSet<>();
        RecyclerView recyclerViewTag = view.findViewById(R.id.recycler_tag);
        recyclerViewTag.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        tagList = new HashSet<>();
        adapterTag = new AdapterTag(tagList);

        recyclerViewTag.setAdapter(adapterTag);
        adapterTag.setTagClickListener(this); // Set the listener in SearchFragment




        recyclerViewTag.setOnTouchListener((v, motionEvent) -> {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                // Disable touch events for other views
                recyclerViewTag.getParent().requestDisallowInterceptTouchEvent(true);
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                // Enable touch events for other views
                recyclerViewTag.getParent().requestDisallowInterceptTouchEvent(true);
            }
            // Return false to allow the ArcSeekBar to handle its own touch events
            return false;
        });




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("testitems")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot document : list) {
                        String name = document.getString("NAME");
                        int price = Objects.requireNonNull(document.getLong("PRICE")).intValue();
                        String description = document.getString("DESCRIPTION");
                        int mrp = Objects.requireNonNull(document.getLong("MRP")).intValue();
                        int inclusions = Objects.requireNonNull(document.getLong("INCLUSIONS")).intValue();
                        String tag = document.getString("CATEGORY");


                        Test test = new Test(name, price, description, mrp, inclusions, tag);

                        testsList.add(test);
                        tagList.add(tag);

                        // Log retrieved data for verification
                        Log.d(TAG, "Test Name: " + name + ", Price: " + price + ", Description: " + description + ", MRP: " + mrp + ", Inclusions: " + inclusions);
                        Log.d(TAG, "Tag: " + tag);
                    }

                    // Set RecyclerView adapter with the retrieved data
                    adapterForTestItem.notifyDataSetChanged();
                    adapterTag.notifyDataSetChanged();


                })
                .addOnFailureListener(e -> {
                    // Log failure message
                    Log.e(TAG, "Error getting documents: ", e);
                });






        searchView = view.findViewById(R.id.search_bar);
        searchView.clearFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query (e.g., presses "Enter" on the keyboard)
                // Perform actions if needed
                searchView.clearFocus();// Assuming adapterForTestItem is your RecyclerView adapter
                return false; // Return true to indicate that the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text changes (i.e., user types/deletes characters)
                // Update the filtered results in your RecyclerView here
                filterList(newText,selectedTagList);// Assuming adapterForTestItem is your RecyclerView adapter
                return true; // Return true to indicate that the query has been handled
            }
        });



        return view;
    }
    private void filterList(String newText, HashSet<String> selectedTags) {
        List<Test> filteredList = new ArrayList<>();

        if (newText.isEmpty() && selectedTags.isEmpty()) {
            // If both search query and tags are empty, show the entire original list
            adapterForTestItem.setFilteredLlist(testsList);

        } else {
            // Filter the list based on the query and selected tags
            for (Test test : testsList) {
                boolean matchesQuery = test.getName().toLowerCase().contains(newText.toLowerCase());

                // Check if the test has any of the selected tags
                boolean matchesTags = false;
                for (String tag : selectedTags) {
                    if (test.getTag().contains(tag)) {
                        matchesTags = true;
                        break;
                    }
                }

                // Include the test in the filtered list if it matches the query or selected tags
                if ((matchesQuery || newText.isEmpty()) && (matchesTags || selectedTags.isEmpty())) {
                    filteredList.add(test);
                }
            }

            if (filteredList.isEmpty()) {
                // Display a message or handle an empty list scenario
                // For example, show "No Test Found" message in the SearchView
                searchView.setQuery("No Test Found", false);
                searchView.clearFocus();
            } else {
                // Show the filtered list in the RecyclerView
                adapterForTestItem.setFilteredLlist(filteredList);
            }
        }
    }

    @Override
    public void onTagClicked(String tag) {
        // Add or remove the clicked tag from the selected tag list
        if (selectedTagList.contains(tag)) {
            selectedTagList.remove(tag); // Deselect tag
        } else {
            selectedTagList.add(tag); // Select tag
        }

        // Update the filtered list based on the selected tags
        filterList(searchView.getQuery().toString(), selectedTagList);
    }
}

