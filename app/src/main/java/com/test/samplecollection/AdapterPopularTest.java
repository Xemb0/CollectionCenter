package com.test.samplecollection;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// The adapter class which
// extends RecyclerView Adapter
public class AdapterPopularTest
        extends RecyclerView.Adapter<AdapterPopularTest.MyView> {

    private List<Test> tests;
    public List<Test> filteredList;
    public AdapterPopularTest(List<Test> tests) {
        this.tests = tests;
    }



    public void setFilteredLlist(List<Test> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    public void updateFilteredList(List<Test> updatedList) {
        this.filteredList = updatedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_test, parent, false);
        return new MyView(view);
    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : tests.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Test currentTest = filteredList != null ? filteredList.get(position) : tests.get(position);
        holder.bind(currentTest);
    }

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView

            extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewDescription;

        public TextView textViewInclusion;
        public TextView textViewMrp;
        public String Inclusion = "Inclusion : ";
        public String testInclusion = " Test";
        public int Inclusions;
        public String totalInclusion;
        public String rupeeSign = "₹ ";

        public String fetchprice;
        public String price;

        public String fetchmrp;
        public String mrp;


        public MyView(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.popular_test_name);

            textViewPrice = itemView.findViewById(R.id.popular_test_price);
            textViewDescription = itemView.findViewById(R.id.popular_test_description);
            textViewMrp = itemView.findViewById(R.id.popular_test_mrp);
            textViewInclusion = itemView.findViewById(R.id.popular_test_inclusions);


        }

        public void bind(Test test) {
            textViewName.setText(test.getName());

            fetchprice = String.valueOf(test.getPrice());
            price = rupeeSign + fetchprice;
            textViewPrice.setText(price);


            fetchmrp = String.valueOf(test.getMrp());
            mrp = rupeeSign + fetchmrp;
            textViewPrice.setText(mrp);


            textViewMrp.setPaintFlags(textViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            textViewDescription.setText(test.getDescription());
            Inclusions = test.getInclusions();
            totalInclusion = Inclusion + Inclusions + testInclusion;
            textViewInclusion.setText(totalInclusion);


            itemView.findViewById(R.id.popular_btn_add_to_cart).setOnClickListener(v -> {
                if (test.isAddedToCart()) {
                    test.setAddedToCart(false);
                    // Update UI for deselection
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.my_tag_deselect));
                } else {
                    test.setAddedToCart(true);
                    // Update UI for selection
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.my_tag_select));
                }
            });
        }
    }
}
