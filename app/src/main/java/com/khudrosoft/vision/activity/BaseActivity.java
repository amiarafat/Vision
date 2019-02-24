package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.khudrosoft.vision.utility.AppSharedPreference;
import com.khudrosoft.vision.utility.ApplicationData;


/**
 * Created by Shihab on 11/24/2016.
 */

abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private ProgressDialog mProgressDialogNotCancellable;
    public AppSharedPreference prefsValues;
    Context context;

    public boolean value = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");


        mProgressDialogNotCancellable = new ProgressDialog(this);
        mProgressDialogNotCancellable.setIndeterminate(true);
        mProgressDialogNotCancellable.setMessage("Loading...");
        mProgressDialogNotCancellable.setCancelable(false);

        prefsValues = new AppSharedPreference(context,
                ApplicationData.APP_NAME, Context.MODE_PRIVATE);

    }

    public void showNotCancalableProgress(){
        if (mProgressDialogNotCancellable != null && !mProgressDialogNotCancellable.isShowing()) {
            mProgressDialogNotCancellable.show();
        }
    }

    public void showProgressDialog() {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }
    }

    public void hideNonCancalableProgressDialog() {

        if (mProgressDialogNotCancellable != null && mProgressDialogNotCancellable.isShowing()) {

            mProgressDialogNotCancellable.dismiss();

        }
    }
    void showAlertForInternet(Activity activity) {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(activity);
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


    public void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
