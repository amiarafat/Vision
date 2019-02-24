package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.model.UpdatePassResponse;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.LogMe;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    EditText edtNewPassword, edtRetypeNewPassword;
    Button btn_update_pass;
    private String TAG = "ChangePasswordActivity";
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtRetypeNewPassword = findViewById(R.id.edtRetypeNewPassword);
        btn_update_pass = findViewById(R.id.btn_update_pass);
        btn_update_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_update_pass) {

            if (Connectivity.isConnected(activity)) {
                updatePassWord();
            } else {
                showAlertForInternet(activity);
            }

        }
    }

    private void updatePassWord() {

        try {

            ApplicationData.hideKeyboard(activity);
            showProgressDialog();
//        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), et_user_name.getText().toString());
//        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
//        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), Constants.ACTION_SIGN_IN);

            LogMe.i("TOKEN: ", "updatePassWord" + prefsValues.getToken());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdatePassResponse> call = apiService.updatePassword(prefsValues.getUser_id().toString(),
                    prefsValues.getToken().toString(),
                    edtNewPassword.getText().toString(), edtRetypeNewPassword.getText().toString());

            call.enqueue(new Callback<UpdatePassResponse>() {
                @Override
                public void onResponse(Call<UpdatePassResponse> call, Response<UpdatePassResponse> response) {
                    UpdatePassResponse updatePassResponse = response.body();

                    LogMe.e(TAG, "updatePassword:" + updatePassResponse.getStatus());
                    hideProgressDialog();

                    if (updatePassResponse.getStatus().equals("success")) {

                        showMessage(updatePassResponse.getStatus(), updatePassResponse.getMessage());

                    } else {

                        Toast.makeText(context, "" + updatePassResponse.getStatus(), Toast.LENGTH_SHORT).show();
                        ApplicationData.goToLogInActivity(activity);

                    }

                }

                @Override
                public void onFailure(Call<UpdatePassResponse> call, Throwable t) {
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

    void showMessage(String message, String title) {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(activity);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setTitle(title);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        activity.finish();
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
}
