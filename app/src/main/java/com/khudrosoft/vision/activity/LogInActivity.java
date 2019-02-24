package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.network.LoginResponse;
import com.khudrosoft.vision.utility.ApplicationData;

import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;


public class LogInActivity extends BaseActivity implements View.OnClickListener {

    //TextView textView_sign_up;
    EditText edit_user_id, edit_pass;
    Context context = this;
    //AppSharedPreference prefsValues = null;
    JSONObject jsonObject = new JSONObject();
    Activity activity = this;
    Button btn_sign_in;
    //CheckBox remember_check;
    private String TAG = "LogInActivity";

    public void initialize() {

        edit_user_id = (EditText) findViewById(R.id.edit_user_id);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        //remember_check = (CheckBox) findViewById(R.campaign_name.remember_check);

        btn_sign_in.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        initialize();

        // prefsValues = new AppSharedPreference(context, ApplicationData.APP_NAME, Context.MODE_PRIVATE);

    }


    void showAlertForInternet() {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(LogInActivity.this);
        builder1.setMessage("Please check your internet connection");
        builder1.setCancelable(false);
        builder1.setTitle("Check Your Network");
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
//        builder1.setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int campaign_name) {
//                        ((Activity) LogInActivity.this).finish();
//                    }
//                });
        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public HashMap<String, String> getLogInDetails() {

        /*action: driver_login

        Parameters-> user_name, password,*/

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(new String(ApplicationData.ACTION), ApplicationData.ACTION_DRIVER_LOG_IN);
        params.put(new String("user_name"), edit_user_id.getText().toString());
        params.put(new String("password"), edit_pass.getText().toString());


        return params;
    }


    boolean checkUserInput() {

        edit_user_id.setError(null);
        edit_pass.setError(null);

        // Store values at the time of the login attempt.
        String email = edit_user_id.getText().toString();
        String password = edit_pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

      /*  // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }*/

        // Check for a empty password field, if the user not entered one.
        if (TextUtils.isEmpty(password)) {
            edit_pass.setError(getString(R.string.error_field_required));
            focusView = edit_pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edit_user_id.setError(getString(R.string.error_field_required));
            focusView = edit_user_id;
            cancel = true;
        }
//        else if (!ApplicationData.validateEmail(email)) {
//            et_user_name.setError(getString(R.string.error_invalid_email));
//            focusView = et_user_name;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == btn_sign_in) {

            Log.e("Sign in", "clicked");
            if (Connectivity.isNetworkAvailable(context)) {

                if (!checkUserInput()) {

//                    goToServerForLogIn();

                    getSignInWithRetrofit();

                }


            } else {

                showAlertForInternet();

            }

        }

    }

    private void getSignInWithRetrofit() {

        ApplicationData.hideKeyboard(activity);
        showProgressDialog();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.login(edit_user_id.getText().toString(),
                edit_pass.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                hideProgressDialog();
                if (loginResponse != null) {
                    LogMe.e(TAG, "getSignInWithRetrofit" + loginResponse.getMessage());
                    hideProgressDialog();

                    if (loginResponse.getStatus().equals("success")) {

                        prefsValues.setToken(loginResponse.getToken());
                        prefsValues.setUser_id(loginResponse.getUser_id());
                        // Toast.makeText(context, "" + loginResponse.getProducts()
                        //      + prefsValues.getToken(), Toast.LENGTH_SHORT).show();

                        ApplicationData.goToHomeActivity(activity);
                        activity.finish();

                    } else {

                        Toast.makeText(context, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                /*if (loginResponse.getPassenger_login().equals("1")) {
                    showToastMessage(loginResponse.getData());

                    prefsValues.setWill_be_logged(true);
                    prefsValues.setPassenger_Full_Name(loginResponse.getName());
                    prefsValues.setPassenger_id(loginResponse.getId());
                    prefsValues.setPassenger_User_Name(loginResponse.getUser_name());
                    prefsValues.setPassenger_email(loginResponse.getEmail());
                    prefsValues.setContactNo(loginResponse.getPhone());

                    prefsValues.setPicture_name(loginResponse.getProfile_picture());
                    LogMe.e("Pic URL", "" + "" + prefsValues.getPicture_name());

                    // will set according to remember me text
                    prefsValues.setWill_be_logged(remember_check.isChecked());

                    LogMe.e("Log", "" + prefsValues.getPassenger_id() + "" + prefsValues.getPassenger_Full_Name());

                    ApplicationData.goToHomeActivity(activity);

                } else {

                    showToastMessage(loginResponse.getData());
                }*/
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
//                    showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());
                //Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
