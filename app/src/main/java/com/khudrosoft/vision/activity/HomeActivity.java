package com.khudrosoft.vision.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khudrosoft.vision.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.model.RxDetails;
import com.khudrosoft.vision.model.RxUser;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "HomeActivity";
    HomeActivity homeActivity = this;
    //AppSharedPreference prefsValues;
    TextView txTotal, txLastDay, txLastMonth;
    private de.hdodenhof.circleimageview.CircleImageView profileImageView;
    private TextView user_Name;

    ImageView btn_add_new_rx, btn_newCampaign, btn_maxpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //prefsValues = new AppSharedPreference(context, ApplicationData.APP_NAME, Context.MODE_PRIVATE);


        txTotal = findViewById(R.id.txTotal);
        txLastDay = findViewById(R.id.txLastDay);
        txLastMonth = findViewById(R.id.txLastMonth);

        profileImageView = findViewById(R.id.profilePicture);
        user_Name = findViewById(R.id.user_Name);
        btn_add_new_rx = findViewById(R.id.btn_add_new_rx);
        btn_newCampaign = findViewById(R.id.btn_newCampaign);
        //btn_maxpro = findViewById(R.campaign_name.btn_maxpro);
        btn_newCampaign.setOnClickListener(this);
        btn_add_new_rx.setOnClickListener(this);

        //btn_maxpro.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Connectivity.isConnected(homeActivity)) {
            getRxDetails();
        } else {
            showAlertForInternet(homeActivity);
        }
    }

    private void getRxDetails() {

        try {

            ApplicationData.hideKeyboard(homeActivity);
            showProgressDialog();
//        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), et_user_name.getText().toString());
//        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
//        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), Constants.ACTION_SIGN_IN);

            LogMe.i("TOKEN: ", "getRxDetails" + prefsValues.getToken());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<RxDetails> call = apiService.getUserRxDetails(prefsValues.getUser_id().toString(),
                    prefsValues.getToken().toString());

            call.enqueue(new Callback<RxDetails>() {
                @Override
                public void onResponse(Call<RxDetails> call, Response<RxDetails> response) {
                    RxDetails rxDetailsResponse = response.body();

                    LogMe.e(TAG, "getUserRxDetails:" + rxDetailsResponse.getStatus());
                    hideProgressDialog();

                    if (rxDetailsResponse.getStatus().equals("success")) {


                        txLastDay.setText("Last Day Rx Submitted: " + rxDetailsResponse.getTotalLastDayRx());
                        txLastMonth.setText("Last Month Rx Submitted: " + rxDetailsResponse.getTotalMonthRx());
                        txTotal.setText("Total Rx Submitted: " + rxDetailsResponse.getTotalRx());
                        String imageURl = rxDetailsResponse.getImageUrl();

                        RxUser rxUser = rxDetailsResponse.getUserRow();
                        String image_name = rxUser.getImage_name();
                        String name = rxUser.getName();
                        user_Name.setText(name);

                        Picasso.get().load(imageURl + image_name).into(profileImageView);

                        //Toast.makeText(context, "" + loginResponse.getStatus() + rxUser.getName(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, "" + rxDetailsResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        ApplicationData.goToLogInActivity(homeActivity);

                    }

                }

                @Override
                public void onFailure(Call<RxDetails> call, Throwable t) {
                    // Log error here since request failed
                    LogMe.e(TAG, t.toString());
                    //   showProgress(false);
                    hideProgressDialog();
                    showToastMessage(t.toString());

                }
            });
        } catch (Exception e) {

            Toast.makeText(context, "Error at getRxDetails", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (campaign_name == R.campaign_name.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action

            ApplicationData.goToHomeActivity(homeActivity);

        } else if (id == R.id.nav_profile) {

            ApplicationData.goToUpdateProfileActivity(homeActivity);

        } else if (id == R.id.nav_uppass) {

            ApplicationData.goToUpPassActivity(homeActivity);

        } else if (id == R.id.nav_target) {

//            ApplicationData.goTargetActivity(homeActivity);
            Intent intent = new Intent(homeActivity, SelectCampaignActivity.class);
            intent.putExtra(ApplicationData.MOVE_TO_TARGET,
                    ApplicationData.MOVE_TO_TARGET_VALUE);
            homeActivity.startActivity(intent);

        } else if (id == R.id.nav_birthday) {

            ApplicationData.goToBirthDayActivity(homeActivity);


        } else if (id == R.id.nav_notice) {

            ApplicationData.goToNoticeList(homeActivity);


        } else if (id == R.id.nav_help) {

            ApplicationData.goToHelpActivity(homeActivity);


        } else if (id == R.id.nav_about) {

            ApplicationData.gotoAboutActivity(homeActivity);


        } else if (id == R.id.nav_logout) {

            prefsValues.clearPreference();
            ApplicationData.goToLogInActivity(homeActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_add_new_rx) {

            startActivity(new Intent(homeActivity, SubmitRxActivity.class));
            //startActivity(new Intent(homeActivity, SelectCampaignActivity.class));

        } else if (v == btn_newCampaign) {

            startActivity(new Intent(homeActivity, SelectCampaignActivity.class));
            //startActivity(new Intent(homeActivity, CampaignActivity.class));
        } /*else if (v == btn_maxpro) {

            startActivity(new Intent(homeActivity, MaxproCampaignActivity.class));
        }*/
    }
}
