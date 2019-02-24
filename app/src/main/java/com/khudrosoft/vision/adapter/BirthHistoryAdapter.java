package com.khudrosoft.vision.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.SuggestedDoctors;

/**
 * Created by Shihab on 1/28/2016.
 */
public class BirthHistoryAdapter extends RecyclerView.Adapter<BirthHistoryAdapter.MyViewHolder> {
    private List<SuggestedDoctors> doctorList;

    // birthdate, Doctor Name, Doctor ID, Doctor Mobile Number
    final String BIRTHDATE = "Birth Day: ";
    final String DOCTOR_NAME = "Doctor Name: ";
    final String DOCTOR_ID = "Doctor ID: ";
    final String MOBILE_NUMBER = "Mobile Number: ";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, fare, name, doctor_id, doctor_mobile_number;
        ImageView cardimage;

        public MyViewHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.txtViewDoctorBirthDay);
            name = (TextView) view.findViewById(R.id.txtDoctorName);
            doctor_id = (TextView) view.findViewById(R.id.txtViewDoctorId);
            doctor_mobile_number = (TextView) view.findViewById(R.id.txtViewDoctorPhoneNumber);
        }
    }


    public BirthHistoryAdapter(ArrayList<SuggestedDoctors> moviesList) {
        this.doctorList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_each_row_birthdays_notifications, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {

            SuggestedDoctors tripHistory = doctorList.get(position);

            holder.date.setText(BIRTHDATE + tripHistory.getBirth_day());
            holder.name.setText(DOCTOR_NAME + tripHistory.getName());
            holder.doctor_id.setText(DOCTOR_ID + tripHistory.getDoctor_id());
            holder.doctor_mobile_number.setText(MOBILE_NUMBER + tripHistory.getMobile_no());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}
