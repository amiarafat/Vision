package com.khudrosoft.vision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.SuggestedProducts;

public class CustomDrugSpinnerAdapter extends BaseAdapter {

    LayoutInflater inflter;
    private ArrayList<SuggestedProducts> fieldOfficers;
    private Context context;


    public CustomDrugSpinnerAdapter(Context context, ArrayList<SuggestedProducts> fieldOfficers) {
        this.context = context;
        this.fieldOfficers = fieldOfficers;
        this.inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        if (fieldOfficers != null) {
            return fieldOfficers.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.custom_spinner, null);
        TextView tv = (TextView) convertView.findViewById(R.id.item_name);
        tv.setText(fieldOfficers.get(position).getName());
        return convertView;


    }
}
