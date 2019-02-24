package com.khudrosoft.vision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.khudrosoft.vision.R;

import java.util.ArrayList;
import java.util.List;


public class PeopleAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    int textViewResourceId;
    List<String> mList, filteredPeople, mListAll;

    public PeopleAdapter(Context context, int resource, int textViewResourceId,
                         List<String> mList) {
        super(context, resource, textViewResourceId, mList);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.mList = mList;
        mListAll = mList;
        filteredPeople = new ArrayList<String>();
    }

    @Override
    public String getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            view = inflater.inflate(R.layout.iteam_search, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.item_name);
            textView.setText(mList.get(position).toString());
        }
        String people = mList.get(position);
        if (people != null) {
            TextView textView = (TextView) view.findViewById(R.id.item_name);
            if (textView != null) {
                textView.setText(people);
            }

        }
        return view;
    }

    @Override
    public Filter getFilter() {

        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            List<String> filteredList = (List<String>) results.values;

            if (results != null && results.count > 0) {
                clear();
                for (String people : filteredList) {
                    add(people);
                }
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                filteredPeople.clear();
                for (String people : mListAll) {
                    if (people.contains(constraint)) {
                        filteredPeople.add(people);
                    }
                }
                filterResults.values = filteredPeople;
                filterResults.count = filteredPeople.size();
            }
            return filterResults;
        }
    };

}
