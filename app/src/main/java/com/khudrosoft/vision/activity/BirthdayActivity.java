package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.RecyclerTouchListener;
import com.khudrosoft.vision.adapter.BirthHistoryAdapter;
import com.khudrosoft.vision.model.BirthDay;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;

public class BirthdayActivity extends BaseActivity {

    private static final String TAG = "Trip History";
    private RecyclerView recyler_history;
    private BirthHistoryAdapter myAdapter;
    List<SuggestedDoctors> tripHistories = new ArrayList<SuggestedDoctors>();
    Activity activity;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        recyler_history = (RecyclerView) findViewById(R.id.recyler_history);
        emptyView = (TextView) findViewById(R.id.empty_view);

        recyler_history.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyler_history.setLayoutManager(mLayoutManager);
        recyler_history.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyler_history.setItemAnimator(new DefaultItemAnimator());

        recyler_history.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyler_history,
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

        if (Connectivity.isConnected(activity)) {

            getDoctorBirthDayList();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDoctorBirthDayList() {

        ApplicationData.hideKeyboard(activity);
        showProgressDialog();
//        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), et_user_name.getText().toString());
//        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
//        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), Constants.ACTION_SIGN_IN);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BirthDay> call = apiService.getDoctorsBirthdayList(prefsValues.getUser_id().toString(),
                prefsValues.getToken().toString());

        call.enqueue(new Callback<BirthDay>() {
            @Override
            public void onResponse(Call<BirthDay> call, Response<BirthDay> response) {
                BirthDay birthDayResponse = response.body();

                LogMe.e(TAG, "getDoctorsBirthdayList:" + birthDayResponse.getStatus());
                hideProgressDialog();

                if (birthDayResponse.getStatus().equals("success")) {

                    myAdapter = new BirthHistoryAdapter(new ArrayList<SuggestedDoctors>
                            (Arrays.asList(birthDayResponse.getDoctorRows())));
                    recyler_history.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(context, "" + birthDayResponse.getStatus(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<BirthDay> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                //   showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });
    }
}
