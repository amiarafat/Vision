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

/**
 * Created by Shihab on 1/28/2016.
 */

/**
 * Created by Shihab on 1/28/2016.
 */
public class CustomDoctorAdapter extends ArrayAdapter<SuggestedDoctors> {
    private final Context mContext;
    private final int itemLayout;
    private List<SuggestedDoctors> doctorList;
    private List<SuggestedDoctors> dataListAllItems;

    public CustomDoctorAdapter(Context context, int resource, List<SuggestedDoctors> moviesList) {
        super(context, resource, moviesList);

        mContext = context;
        itemLayout = resource;
        this.doctorList = moviesList;
    }


    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public SuggestedDoctors getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        SuggestedDoctors suggestedDoctors = doctorList.get(position);

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
                ArrayList<SuggestedDoctors> suggestions = new ArrayList<SuggestedDoctors>();
                for (SuggestedDoctors customer : doctorList) {
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
                addAll((ArrayList<SuggestedDoctors>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(doctorList);
            }
            notifyDataSetChanged();
        }
    };

    private  ListFilter listFilter = new ListFilter();

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<SuggestedDoctors>(doctorList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<SuggestedDoctors> matchValues = new ArrayList<SuggestedDoctors>();

                for (SuggestedDoctors dataItem : dataListAllItems) {
                    if (dataItem.getName().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }

                    /*if (dataItem.getName().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }*/
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                doctorList = (ArrayList<SuggestedDoctors>) results.values;
            } else {
                doctorList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                // notifyDataSetInvalidated();
            }
        }

    }


    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }
}
