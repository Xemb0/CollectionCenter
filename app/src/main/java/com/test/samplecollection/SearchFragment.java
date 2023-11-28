package com.test.samplecollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.test.samplecollection.TestInfo;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        CardView test_1 = view.findViewById(R.id.test_1);

        test_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity (replace NewActivity.class with the actual class name)
                Intent intent = new Intent(getActivity(), TestInfo.class);

                // Start the new activity
                startActivity(intent);
            }
        });
        return view;
    }
}
