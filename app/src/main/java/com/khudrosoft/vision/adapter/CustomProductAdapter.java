package com.khudrosoft.vision.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.model.SuggestedProducts;

/**
 * Created by Shihab on 1/28/2016.
 */
public class CustomProductAdapter extends ArrayAdapter<SuggestedProducts> {
    private final Context mContext;
    private final int itemLayout;
    private List<SuggestedProducts> productList;

    public CustomProductAdapter(Context context, int resource, List<SuggestedProducts> moviesList) {
        super(context, resource, moviesList);

        mContext = context;
        itemLayout = resource;
        this.productList = moviesList;
    }


    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public SuggestedProducts getItem(int position) {
        return productList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        SuggestedProducts suggestedDoctors = productList.get(position);

        TextView strName = (TextView) view.findViewById(R.id.item_name);
        strName.setText(suggestedDoctors.getName());
        return view;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((SuggestedDoctors) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<SuggestedProducts> suggestions = new ArrayList<SuggestedProducts>();
                for (SuggestedProducts customer : productList) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<SuggestedProducts>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(productList);
            }
            notifyDataSetChanged();
        }
    };


}
