package com.test.samplecollection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeFragment extends Fragment implements CitySelectListener {
    private ViewPager2 viewPager;
    private ArrayList<Integer> imageList;
    private ConstraintLayout root;

    private ImageSlider imageSlider;
    private Handler handler;

    private RecyclerView HorizontalRv;
    private LinearLayoutManager linearLayoutManager;
    private HorizontallAdaptor horizontallAdaptor;

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

    Button cityselectbutton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        setUpTransformer();

        // initialisation with id's
        recyclerView = view.findViewById(R.id.recyclerview);
        cityselectbutton = view.findViewById(R.id.select_city);
        RecyclerViewLayoutManager = new LinearLayoutManager(requireContext());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        // calling constructor of adapter
        // with source list as a parameter
        adapter = new ScrollView_Adapter(source);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(adapter);

        HorizontalRv = view.findViewById(R.id.horizontalRv);
        // Use the `linearLayoutManager` to set the layout manager for your `HorizontalRv`.
        HorizontalRv.setLayoutManager(linearLayoutManager);
        horizontallAdaptor = new HorizontallAdaptor(imageList);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });

        cityselectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CitySelectBottomSheet bottomSheetFragment = new CitySelectBottomSheet(HomeFragment.this);
                bottomSheetFragment.show(requireActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });





        return view;
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

    private void setUpTransformer() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.14f);
        });

        viewPager.setPageTransformer(transformer);
    }

    private void init(View view) {
        viewPager = view.findViewById(R.id.viewimage);
        handler = new Handler(Looper.getMainLooper());
        imageList = new ArrayList<>();

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

    class HorizontallAdaptor extends RecyclerView.Adapter<HorizontallAdaptor.HorizontalHolder> {
        ArrayList<Integer> image;

        public HorizontallAdaptor(ArrayList<Integer> image) {
            this.image = image;
        }

        @NonNull
        @Override
        public HorizontallAdaptor.HorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
            return new HorizontalHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HorizontalHolder horizontallAdaptor, int position) {
            horizontallAdaptor.horizontalImage.setImageResource(image.get(position));
        }

        @Override
        public int getItemCount() {
            return image.size();
        }

        class HorizontalHolder extends RecyclerView.ViewHolder {
            ImageView horizontalImage;

            public HorizontalHolder(@NonNull View itemView) {
                super(itemView);
                horizontalImage = itemView.findViewById(R.id.horizontal_image);
            }
        }
    }



    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add("gfg");
        source.add("is");
        source.add("best");
        source.add("site");
        source.add("for");
        source.add("interview");
        source.add("preparation");
    }

    @Override
    public void onCitySelected(String selectedCity) {
        // Handle the selected city here
        // For example, update the UI with the selected city
        cityselectbutton.setText(selectedCity);
    }

    // ... (Add the rest of your code)
}
