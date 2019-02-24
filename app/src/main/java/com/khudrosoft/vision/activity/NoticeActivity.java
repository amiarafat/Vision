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
import com.khudrosoft.vision.adapter.NoticeAdapter;
import com.khudrosoft.vision.adapter.RecyclerTouchListener;
import com.khudrosoft.vision.model.NoticeResponse;
import com.khudrosoft.vision.model.NoticeRows;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;

public class NoticeActivity extends BaseActivity {

    private static final String TAG = "NoticeActivity";
    private RecyclerView recyler_notice;
    private NoticeAdapter myAdapter;
    List<SuggestedDoctors> tripHistories = new ArrayList<SuggestedDoctors>();
    Activity activity;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        recyler_notice = (RecyclerView) findViewById(R.id.recyler_notice);
        emptyView = (TextView) findViewById(R.id.empty_view);

        recyler_notice.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyler_notice.setLayoutManager(mLayoutManager);
        recyler_notice.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyler_notice.setItemAnimator(new DefaultItemAnimator());

        recyler_notice.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyler_notice,
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

            getNoticeList();
        }
    }

    private void getNoticeList() {

        ApplicationData.hideKeyboard(activity);
        showProgressDialog();
//        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), et_user_name.getText().toString());
//        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
//        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), Constants.ACTION_SIGN_IN);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NoticeResponse> call = apiService.getNoticeList(prefsValues.getUser_id().toString(),
                prefsValues.getToken().toString());

        call.enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                NoticeResponse birthDayResponse = response.body();

                LogMe.e(TAG, "getNoticeList:" + birthDayResponse.getStatus());
                hideProgressDialog();

                if (birthDayResponse.getStatus().equals("success")) {

                    myAdapter = new NoticeAdapter(new ArrayList<NoticeRows>
                            (Arrays.asList(birthDayResponse.getNoticeRows())));

                    recyler_notice.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(context, "" + birthDayResponse.getStatus(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                //   showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });
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

}
