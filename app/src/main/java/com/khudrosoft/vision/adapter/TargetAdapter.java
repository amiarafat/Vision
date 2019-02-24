package com.khudrosoft.vision.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khudrosoft.vision.R;

import java.util.ArrayList;


/**
 * Created by Shihab on 1/28/2016.
 */
public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.MyViewHolder> {
    private ArrayList<String> tagetList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView data, titel;
        RelativeLayout top;

        public MyViewHolder(View view) {
            super(view);
            // data = (TextView) view.findViewById(R.id.data);
            titel = (TextView) view.findViewById(R.id.titel);
            top = view.findViewById(R.id.top);
        }
    }

    Context context;

    public TargetAdapter(Context context, ArrayList<String> moviesList) {
        this.context = context;
        this.tagetList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_target, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            //holder.data.setText("" + tagetList.get(position).toString());
            holder.titel.setText("" + tagetList.get(position).toString());
            /*if (position == 0) {
                holder.top.setBackgroundColor(ContextCompat.getColor(context, R.color.sales_up));
            } else if (position == 1 || position == 2) {
                holder.top.setBackgroundColor(ContextCompat.getColor(context, R.color.single_thailand));
            } else if (position == 3 || position == 4) {
                holder.top.setBackgroundColor(ContextCompat.getColor(context, R.color.family_india));
            } else if (position == 5 || position == 6) {
                holder.top.setBackgroundColor(ContextCompat.getColor(context, R.color.family_thailand));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return tagetList.size();
    }
}
