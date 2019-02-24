package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.RecyclerTouchListener;
import com.khudrosoft.vision.adapter.TargetAdapter;
import com.khudrosoft.vision.model.TargetResponse;
import com.khudrosoft.vision.model.TargetRows;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;

public class TargetActivty extends BaseActivity {

    private static final String TAG = "TargetActivty";
    private RecyclerView recyler_target;
    private TargetAdapter myAdapter;
    Activity activity;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        activity = this;

        camp_id = getIntent().getStringExtra(ApplicationData.CAMPAIGN_NAME);

        recyler_target = (RecyclerView) findViewById(R.id.recyler_target);
        emptyView = (TextView) findViewById(R.id.empty_view);

        recyler_target.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyler_target.setLayoutManager(mLayoutManager);
        recyler_target.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyler_target.setItemAnimator(new DefaultItemAnimator());

        recyler_target.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyler_target,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

//                        TripHistory movie = paymentList.get(position);
//                        Toast.makeText(getApplicationContext(),
//                                movie.getTrip_date() + " is selected!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                        /*Toast.makeText(getApplicationContext(),
                                " Long Clicked!", Toast.LENGTH_SHORT).show();*/

                    }
                }));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Connectivity.isConnected(activity)) {

            getTargetDeatils();
        }



    }
    String camp_id;

    private void getTargetDeatils() {
        ApplicationData.hideKeyboard(activity);
        showProgressDialog();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TargetResponse> call = apiService.getTargetResponse(
                prefsValues.getUser_id().toString(),
                prefsValues.getToken().toString(),camp_id);

        call.enqueue(new Callback<TargetResponse>() {
            @Override
            public void onResponse(Call<TargetResponse> call, Response<TargetResponse> response) {
                TargetResponse targetResponse = response.body();

                LogMe.e(TAG, "getTargetDetails:" + targetResponse.getStatus());
                hideProgressDialog();

                if (targetResponse.getStatus().equals("success")) {

                    ArrayList<String> data = new ArrayList<String>();

                    if (targetResponse.getTargetRows().length > 0) {

                        TargetRows targetRows = targetResponse.getTargetRows()[0];

                        String LABEL_RUS_ACHE = "Date: " + targetRows.getUpto_date();
                        String LABEL_SALES = "Sales Requirement :" + targetRows.getSales_target();
                        String LABEL_REQ = "Sales Remaining \n" + targetRows.getSales_remaining();
                        String SINGLE_ACHE = "Rx Image Submit Requirement \n" + targetRows.getRx_image_requirement();
                        String LABEL_FAM_IND = "Rx Image Submit Remaining \n" + targetRows.getRx_image_remaining();
                        String LABEL_FAM_ACH = "4P Rx Image Submit Requirement \n" + targetRows.getRx_image_requirement_4p();
                        String LABEL_RUS = "4P Rx Image Submit Remaining \n" + targetRows.getRx_image_remaining_4p();


                        data.add(LABEL_RUS_ACHE);
                        data.add(LABEL_SALES);
                        data.add(LABEL_REQ);
                        data.add(SINGLE_ACHE);
                        data.add(LABEL_FAM_IND);
                        data.add(LABEL_FAM_ACH);
                        data.add(LABEL_RUS);

                    }

                    myAdapter = new TargetAdapter(activity,data);

                    recyler_target.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(context, "" + targetResponse.getStatus(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<TargetResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                //   showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });

    }

}
