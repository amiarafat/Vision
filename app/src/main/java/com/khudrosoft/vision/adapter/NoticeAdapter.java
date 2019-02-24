package com.khudrosoft.vision.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.NoticeRows;

/**
 * Created by Shihab on 1/28/2016.
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    private ArrayList<NoticeRows> noticeRowsList;

    //    Notice Id, Notice Date, Notice
    // birthdate, Doctor Name, Doctor ID, Doctor Mobile Number
    final String NOTICE_DATE = "Notice Date: ";
    final String NOTICE = "Notice: ";
    final String NOTICE_Id = "Notice Id: ";


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, notice,id;


        public MyViewHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.txtViewDate);
            notice = (TextView) view.findViewById(R.id.txtViewNotice);
            id = (TextView) view.findViewById(R.id.txtNoticeId);

        }
    }


    public NoticeAdapter(ArrayList<NoticeRows> moviesList) {

        this.noticeRowsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_each_row_news_feed, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            NoticeRows noticeRows = noticeRowsList.get(position);

            holder.date.setText(NOTICE_DATE + noticeRows.getN_date());
            holder.notice.setText(NOTICE + noticeRows.getNotice());
            holder.id.setText(NOTICE + noticeRows.getNotice_id());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return noticeRowsList.size();
    }
}
