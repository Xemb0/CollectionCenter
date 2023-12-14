package com.test.samplecollection;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForTestItem extends RecyclerView.Adapter<AdapterForTestItem.ViewHolder> {
    private List<Test> tests;
    public List<Test> filteredList;

    public AdapterForTestItem(List<Test> tests) {
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






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : tests.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test currentTest = filteredList != null ? filteredList.get(position) : tests.get(position);
        holder.bind(currentTest);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewDescription;

        public TextView textViewInclusion;
        public TextView textViewMrp;
        public String Inclusion="Inclusion : ";
        public String testInclusion=" Test";
        public  int Inclusions;
        public String totalInclusion;
        public String rupeeSign ="₹ ";

        public String fetchprice;
        public String price;

        public String fetchmrp;
        public String mrp;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.test_name);

            textViewPrice = itemView.findViewById(R.id.test_price);
            textViewDescription = itemView.findViewById(R.id.test_description);
            textViewMrp = itemView.findViewById(R.id.test_mrp);
            textViewInclusion = itemView.findViewById(R.id.test_inclusions);



        }

        public void bind(Test test) {
            textViewName.setText(test.getName());

            fetchprice = String.valueOf(test.getPrice());
            price = rupeeSign+ fetchprice;
            textViewPrice.setText(price);



            fetchmrp = String.valueOf(test.getMrp());
            mrp = rupeeSign+ fetchmrp;
            textViewPrice.setText(mrp);


            textViewMrp.setPaintFlags(textViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            textViewDescription.setText(test.getDescription());
            Inclusions= test.getInclusions();
            totalInclusion = Inclusion + Inclusions + testInclusion;
            textViewInclusion.setText(totalInclusion);
        }
    }
}
