package com.khudrosoft.vision.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.CustomCampaignListAdapter;
import com.khudrosoft.vision.adapter.CustomDoctorAdapter;
import com.khudrosoft.vision.model.CampaignRows;
import com.khudrosoft.vision.model.GetCampaignResponse;
import com.khudrosoft.vision.model.RxDetails;
import com.khudrosoft.vision.model.RxUser;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.model.SuggestedProducts;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.LogMe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCampaignActivity extends BaseActivity {

    private static final String TAG = "SelectCampaignActivity";
    Button btnSubmit;
    SelectCampaignActivity activity;
    Spinner spinnerCampaign;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_campaign);

        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        value = getIntent().getIntExtra(ApplicationData.MOVE_TO_TARGET, 2);

        btnSubmit = findViewById(R.id.btnSubmit);

        spinnerCampaign = findViewById(R.id.spinnerCampaign);
        spinnerCampaign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);

                if (object instanceof CampaignRows) {
                    CampaignRows camp_id = (CampaignRows) object;
                    String name = camp_id.getCamp_id();
                    Toast.makeText(getApplicationContext(),
                            name, Toast.LENGTH_SHORT).show();
                }
                ApplicationData.hideKeyboard(activity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                if (value == ApplicationData.MOVE_TO_TARGET_VALUE) {

                    spinnerCampaign.getSelectedItemPosition();

                    intent = new Intent(activity, TargetActivty.class);
                    intent.putExtra(ApplicationData.CAMPAIGN_NAME,
                            spinnerCampaign.getSelectedItem().toString());
                    startActivity
                            (intent);

                } else {

                    intent = new Intent(activity, CampaignActivity.class);
                    intent.putExtra(ApplicationData.CAMPAIGN_NAME,
                            spinnerCampaign.getSelectedItem().toString());
                    startActivity
                            (intent);

                }
            }
        });

        getCampaignList();

    }


    private void getCampaignList() {

        try {

            ApplicationData.hideKeyboard(activity);
            showProgressDialog();

            //LogMe.i("TOKEN: ", "getCampaignList" + prefsValues.getToken());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetCampaignResponse> call = apiService.
                    getCampaignList(prefsValues.getUser_id().toString(),
                            prefsValues.getToken().toString());

            call.enqueue(new Callback<GetCampaignResponse>() {
                @Override
                public void onResponse(Call<GetCampaignResponse> call, Response<GetCampaignResponse> response) {
                    GetCampaignResponse rxDetailsResponse = response.body();

                    LogMe.e(TAG, "getCampaignList:" + rxDetailsResponse.toString());
                    hideProgressDialog();

                    if (rxDetailsResponse.getStatus().equals("success")) {


                        CustomCampaignListAdapter stringArrayAdapterProdyct =
                                new CustomCampaignListAdapter(activity,
                                        R.layout.custom_spinner,
                                        new ArrayList<CampaignRows>
                                                (Arrays.asList(rxDetailsResponse.getCampaignRows())));

                        stringArrayAdapterProdyct.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinnerCampaign.setAdapter(stringArrayAdapterProdyct);

                    } else {

                        Toast.makeText(context, "" + rxDetailsResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        ApplicationData.goToLogInActivity(activity);

                    }

                }

                @Override
                public void onFailure(Call<GetCampaignResponse> call, Throwable t) {
                    // Log error here since request failed
                    LogMe.e(TAG, t.toString());
                    //   showProgress(false);
                    hideProgressDialog();
                    showToastMessage(t.toString());

                }
            });
        } catch (Exception e) {

            Toast.makeText(context, "Error at getCampaignList", Toast.LENGTH_SHORT).show();
        }

    }
}
