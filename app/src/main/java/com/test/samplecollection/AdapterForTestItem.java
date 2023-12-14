package com.test.samplecollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForTestItem extends RecyclerView.Adapter<AdapterForTestItem.ViewHolder> {
    private List<Test> tests;

    public AdapterForTestItem(List<Test> tests) {
        this.tests = tests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_tems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test currentTest = tests.get(position);
        holder.bind(currentTest);
    }




    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewDescription;

        public TextView textViewInclusion;
        public TextView textViewMrp;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.test_name);
            textViewPrice = itemView.findViewById(R.id.test_price);
            textViewDescription = itemView.findViewById(R.id.test_description);
            textViewInclusion = itemView.findViewById(R.id.test_inclusions);
            textViewMrp = itemView.findViewById(R.id.test_mrp);


        }

        public void bind(Test test) {
            textViewName.setText(test.getName());
            textViewPrice.setText(String.valueOf(test.getPrice()));
            textViewDescription.setText(test.getDescription());
            textViewInclusion.setText(test.getInclusions());
            textViewMrp.setText(String.valueOf(test.getMrp()));
        }
    }
}
