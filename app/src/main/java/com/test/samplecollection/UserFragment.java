package com.test.samplecollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

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
}
