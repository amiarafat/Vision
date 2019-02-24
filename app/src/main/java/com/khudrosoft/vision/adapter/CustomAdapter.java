package com.khudrosoft.vision.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.khudrosoft.vision.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shihab on 1/28/2016.
 */
public class CustomAdapter extends ArrayAdapter {
    private final Context mContext;
    private final int itemLayout;
    private ArrayList<String> dataListAllItems;
    private List<String> dataList;

    private ListFilter listFilter = new ListFilter();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titel;


        public MyViewHolder(View view) {
            super(view);
            // data = (TextView) view.findViewById(R.id.data);
            titel = (TextView) view.findViewById(R.id.item_name);
        }
    }


    public CustomAdapter(Context context, int resource, List<String> moviesList) {
        super(context, resource, moviesList);

        mContext = context;
        itemLayout = resource;
        this.dataList = moviesList;
    }


    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<String>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<String> matchValues = new ArrayList<String>();

                for (String dataItem : dataListAllItems) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<String>) results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                // notifyDataSetInvalidated();
            }
        }

    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        Log.d("CustomListAdapter",
                dataList.get(position));
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.item_name);
        strName.setText(getItem(position));
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }


    void clearData(){


    }
}
