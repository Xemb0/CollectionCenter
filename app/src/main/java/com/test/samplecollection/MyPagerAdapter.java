package com.test.samplecollection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.test.samplecollection.HomeFragment;
import com.test.samplecollection.SearchFragment;
import com.test.samplecollection.UserFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 3;

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return the fragment based on the position
        switch (position) {
            case 0:
                return new SearchFragment();
            case 1:
                return new HomeFragment(); // Return a default fragment if needed
            default:
                return new UserFragment();
        }
    }



    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
