package com.test.samplecollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.test.samplecollection.CitySelectListener;

import java.util.ArrayList;
import java.util.List;

public class CitySelectBottomSheet extends BottomSheetDialogFragment {

    // Listener to communicate city selection
    private CitySelectListener citySelectListener;

    public CitySelectBottomSheet(CitySelectListener listener) {
        this.citySelectListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_select_city_bottomsheet, container, false);

        // Get the ListView
        ListView cityListView = view.findViewById(R.id.cityListView);

        // Create a list of cities (replace with your actual city data)
        List<String> cityList = new ArrayList<>();
        cityList.add("Rishikesh");
        cityList.add("Haridwar");
        cityList.add("Dehradun");
        cityList.add("Shyampur");
        cityList.add("Gumaniwala");
        cityList.add("Doiwala");
        // ... add more cities

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, cityList);
        cityListView.setAdapter(adapter);

        // Set a click listener for city selection
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected city
                String selectedCity = (String) parent.getItemAtPosition(position);

                // Notify the listener (HomeFragment) about the selected city
                if (citySelectListener != null) {
                    citySelectListener.onCitySelected(selectedCity);
                }

                // Dismiss the bottom sheet
                dismiss();
            }
        });


        return view;
    }

    // Set the listener for city selection
    public void setCitySelectListener(CitySelectListener listener) {
        this.citySelectListener = listener;
    }

    // Notify the listener about the selected city
    private void notifyCitySelected(String selectedCity) {
        if (citySelectListener != null) {
            citySelectListener.onCitySelected(selectedCity);
        }
    }
}
