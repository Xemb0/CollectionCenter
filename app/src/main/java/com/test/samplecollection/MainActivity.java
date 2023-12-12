package com.test.samplecollection;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.test.samplecollection.HomeFragment;
import com.test.samplecollection.MyPagerAdapter;
import com.test.samplecollection.SearchFragment;
import com.test.samplecollection.UserFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private MeowBottomNavigation bottomNavigation;


    // Recycler View object
    RecyclerView recyclerView;

    // Array list for recycler view data source
    ArrayList<String> source;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // adapter class object
    ScrollView_Adapter adapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    View ChildView;
    int RecyclerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.ViewPager);
        bottomNavigation = findViewById(R.id.Navbar);

        setupViewPager();
        setupBottomNavigation();





    }

    private void setupViewPager() {
        // Create your fragments for each tab
        Fragment[] fragments = new Fragment[]{
                new HomeFragment(),
                new SearchFragment(),
                new UserFragment()
        };

        // Set up ViewPager2 with a custom adapter
        viewPager.setAdapter(new MyPagerAdapter(this));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Update the selected tab in MeowBottomNavigation

                bottomNavigation.show(position, true);
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigation.setBackgroundBottomColor(Color.parseColor("#9EB1A3FF"));
        bottomNavigation.setDefaultIconColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setCircleColor(Color.parseColor("#9EB1A3FF"));
        bottomNavigation.setSelectedIconColor(Color.parseColor("#FFFFFF"));


        bottomNavigation.add(new MeowBottomNavigation.Model(0, R.drawable.ic_search_nav));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_nav));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_user_nav));

        // Set the initial tab
        viewPager.setCurrentItem(1, false);
        bottomNavigation.setOnClickMenuListener(model -> {
            // Handle tab selection
            viewPager.setCurrentItem(model.getId(), false);
            return null;
        });
    }
}
