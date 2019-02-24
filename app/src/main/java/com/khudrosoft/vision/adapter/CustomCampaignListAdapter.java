package com.khudrosoft.vision.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.CampaignRows;

import java.util.List;

/**
 * Created by Shihab on 1/28/2016.
 */

/**
 * Created by Shihab on 1/28/2016.
 */
public class CustomCampaignListAdapter extends ArrayAdapter<CampaignRows> {
    private final Context mContext;
    private final int itemLayout;
    private List<CampaignRows> campaignList;


    public CustomCampaignListAdapter(Context context, int resource,
                                     List<CampaignRows> moviesList) {
        super(context, resource, moviesList);

        mContext = context;
        itemLayout = resource;
        this.campaignList = moviesList;
    }


    @Override
    public int getCount() {
        return campaignList.size();
    }

    @Override
    public CampaignRows getItem(int position) {
        return campaignList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        CampaignRows campaignRows = campaignList.get(position);

        TextView strName = (TextView) view.findViewById(R.id.item_name);
        strName.setText(campaignRows.getCamp_id());
        return view;
    }

}
