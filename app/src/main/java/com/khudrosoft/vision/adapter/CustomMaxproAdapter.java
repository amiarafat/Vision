package com.khudrosoft.vision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.MaxProProducts;

public class CustomMaxproAdapter extends ArrayAdapter<MaxProProducts> {

    LayoutInflater inflter;
    private ArrayList<MaxProProducts> productsList;
    private Context context;


    public CustomMaxproAdapter(Context context, ArrayList<MaxProProducts> fieldOfficers) {
        super(context, R.layout.custom_spinner, fieldOfficers);
        this.context = context;
        this.productsList = fieldOfficers;
        this.inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        if (productsList != null) {
            return productsList.size();
        }
        return 0;
    }

    @Override
    public MaxProProducts getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_spinner, null);

        }
        TextView tv = (TextView) convertView.findViewById(R.id.item_name);
        tv.setText(productsList.get(position).getName());
        return convertView;

    }
}
