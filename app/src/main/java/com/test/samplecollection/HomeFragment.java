package com.test.samplecollection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.samplecollection.kotlin.LoginActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class HomeFragment extends Fragment implements CitySelectListener ,OnTouchListener{
    private ViewPager2 viewPager;
    private ArrayList<Integer> imageList;


    private ConstraintLayout root;
    private ImageSlider imageSlider;
    private Handler handler;
    private RecyclerView recyclerImageSlider;
    private LinearLayoutManager linearLayoutManager;
    private AdapterImageSlider horizontallAdaptor;
    // Recycler View object
    RecyclerView recyclerViewPopularTest;

    // Array list for recycler view data source
    ArrayList<String> popularTestArrayList;

    // Layout Manager


    // adapter class object
    LinearLayoutManager HorizontalLayout;
    Button cityselectbutton;
     ImageView loginUserButton;

    FirebaseAuth firebaseAuth;


    ArrayList<Test> testsList;
    HashSet<String> tagList;
    private static final String TAG = "SearchFragment";

    AdapterPopularTest adapterForPopularTest;
    AdapterTag adapterTag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageSlider(view);
        setUpTransformer();
        PopularTestScrollView(view);
        CitySelect(view);
        LoginUserButton(view);
        return view;
    }

    private void LoginUserButton(View view) {
        loginUserButton = view.findViewById(R.id.userButton);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .circleCrop() // Apply circular crop transformation
                    .into(loginUserButton);


            loginUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "yess", Toast.LENGTH_SHORT).show();
                }
            });

        }else
        {
            loginUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    requireContext().startActivity(intent);
                }
            });

        }

    }

    private void CitySelect(View view) {
        cityselectbutton = view.findViewById(R.id.select_city);
        cityselectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CitySelectBottomSheet bottomSheetFragment = new CitySelectBottomSheet(HomeFragment.this);
                bottomSheetFragment.show(requireActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }

    @Override
    public void onCitySelected(String selectedCity) {
        cityselectbutton.setText(selectedCity);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void PopularTestScrollView(View view) {





        recyclerViewPopularTest = view.findViewById(R.id.recyclerview_popular_test);
        LinearLayoutManager popularTestLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularTest.setLayoutManager(popularTestLayoutManager);
        testsList = new ArrayList<>();
        adapterForPopularTest = new AdapterPopularTest(testsList,this);
        recyclerViewPopularTest.setAdapter(adapterForPopularTest);


        AtomicReference<Float> startX = new AtomicReference<>((float) 0);
        AtomicReference<Float> startY = new AtomicReference<>((float) 0);

// Set touch listener for the RecyclerView
        recyclerViewPopularTest.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Store the initial touch X coordinate
                    startX.set(event.getX());
                    startY.set(event.getY());
                    // Disable touch events for other views initially
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    // Calculate the distance moved in X and Y directions
                    float distanceX = Math.abs(event.getX() - startX.get());
                    float distanceY = Math.abs(event.getY() - startY.get());

                    // Check if the distance moved horizontally is greater than vertically
                    if (distanceX > distanceY) {
                        // Disable touch events for other views if the horizontal movement is larger
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        // Allow touch events for other views if the vertical movement is larger
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Enable touch events for other views
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PopularTests")
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

                        // Log retrieved data for verification
                        Log.d(TAG, "Test Name: " + name + ", Price: " + price + ", Description: " + description + ", MRP: " + mrp + ", Inclusions: " + inclusions);

                    }
                    adapterForPopularTest.notifyDataSetChanged();


                })
                .addOnFailureListener(e -> {
                    // Log failure message
                    Log.e(TAG, "Error getting documents: ", e);
                });







    }





    private void ImageSlider(View view) {
        recyclerImageSlider = view.findViewById(R.id.recycler_image_slider);
        recyclerImageSlider.setLayoutManager(linearLayoutManager);
        horizontallAdaptor = new AdapterImageSlider(imageList);

        viewPager = view.findViewById(R.id.viewimage);
        handler = new Handler(Looper.getMainLooper());
        imageList = new ArrayList<>();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });

        imageList.add(R.drawable.image1);
        imageList.add(R.drawable.image2);
        imageList.add(R.drawable.image3);
        imageList.add(R.drawable.image4);
        imageList.add(R.drawable.image5);

        imageSlider = new ImageSlider(imageList, viewPager);
        viewPager.setAdapter(imageSlider);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void setUpTransformer() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.14f);
        });

        viewPager.setPageTransformer(transformer);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };


    @Override
    public void onTouch() {
        viewPager.setUserInputEnabled(false);
    }
}
