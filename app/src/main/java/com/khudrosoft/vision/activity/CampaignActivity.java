package com.khudrosoft.vision.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.CustomDoctorAdapter;
import com.khudrosoft.vision.adapter.CustomSpinnerAdapter;
import com.khudrosoft.vision.model.CampaignEntryPageResponse;
import com.khudrosoft.vision.model.SubmitCampaignEntryPageResponse;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.model.SuggestedProducts;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.ImagePicker;
import com.khudrosoft.vision.utility.LogMe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CampaignActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CampaignActivity";
    Activity activity;

    AutoCompleteTextView auto_txt_doctor_id;

    TextView txtDate;
    //    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String LONG_DATE_FORMAT = "dd-MM-yyyy";
    ImageView imageViewRxPic;
    Button btnSubmitCampaign;

    private String region, team, camp_id, doctor_id;
    private String productOneValue, productTwoValue;
    private static final int MY_PERMISSIONS_REQUEST_READ_PICTURES = 5252;
    private static final int PICK_IMAGE_ID = 234;
    File file = null;

    Spinner productOneSpinner, productTwoSpinner;
    private Bitmap bitmap;
    private String campaign_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        getWindow().addFlags(WindowManager.
                LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        campaign_name = getIntent().
                getStringExtra(ApplicationData.CAMPAIGN_NAME);


        txtDate = findViewById(R.id.txtDate);
        txtDate.setEnabled(false);
        auto_txt_doctor_id = findViewById(R.id.auto_txt_doctor_id);
        auto_txt_doctor_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object object = parent.getItemAtPosition(position);

                if (object instanceof SuggestedDoctors) {
                    SuggestedDoctors suggestedDoctors = (SuggestedDoctors) object;
                    doctor_id = suggestedDoctors.getDoctor_id();
                    //Toast.makeText(getApplicationContext(), doctorsId, Toast.LENGTH_SHORT).show();
                }

                ApplicationData.hideKeyboard(activity);
            }
        });

        productOneSpinner = findViewById(R.id.spinnerOne);
        productOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);

                if (object instanceof SuggestedProducts) {
                    SuggestedProducts suggestedDoctors = (SuggestedProducts) object;
                    productOneValue = suggestedDoctors.getName();
                    //Toast.makeText(getApplicationContext(), doctorsId, Toast.LENGTH_SHORT).show();
                }
                ApplicationData.hideKeyboard(activity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        productTwoSpinner = findViewById(R.id.spinnerTwo);
        productTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);

                if (object instanceof SuggestedProducts) {
                    SuggestedProducts suggestedProducts = (SuggestedProducts) object;
                    productTwoValue = suggestedProducts.getName();
                    //Toast.makeText(getApplicationContext(), doctorsId, Toast.LENGTH_SHORT).show();
                }
                ApplicationData.hideKeyboard(activity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageViewRxPic = findViewById(R.id.imageViewRxPicCampaign);
        bitmap = ((BitmapDrawable) imageViewRxPic.getDrawable()).getBitmap();

        btnSubmitCampaign = findViewById(R.id.btnSubmitCampaign);


        btnSubmitCampaign.setOnClickListener(this);
        imageViewRxPic.setOnClickListener(this);


        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date());
        txtDate.setText(currentDateandTime);


        if (Connectivity.isConnected(activity)) {

            getCampaignEntryDetails();

        } else {

            showAlertForInternet(this);
        }

        // Toast.makeText(getApplicationContext(), "Work in Progress in this page", Toast.LENGTH_LONG).show();
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

    void getCampaignEntryDetails() {
        ApplicationData.hideKeyboard(activity);
        showProgressDialog();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CampaignEntryPageResponse> call = apiService.
                getCampaignPageEntryResponse(prefsValues.getUser_id().toString(),
                        prefsValues.getToken().toString(), campaign_name);

        call.enqueue(new Callback<CampaignEntryPageResponse>() {
            @Override
            public void onResponse(Call<CampaignEntryPageResponse> call, Response<CampaignEntryPageResponse> response) {
                CampaignEntryPageResponse entryRxObj = response.body();


                LogMe.i(TAG, "getCampaignEntryDetails:" + entryRxObj.toString());
                hideProgressDialog();

                if (entryRxObj.getStatus().equals("success")) {

                    region = entryRxObj.getRegion();
                    team = entryRxObj.getTeam();
                    camp_id = entryRxObj.getCamp_id();

                    LogMe.i(TAG, "CampaignEntryPageResponse:" + region + " : " + team);

                    CustomSpinnerAdapter productAdapter = new CustomSpinnerAdapter(activity,
                            new ArrayList<SuggestedProducts>(Arrays.asList(entryRxObj.getProducts())));
                    productAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    CustomDoctorAdapter doctorAdapter =
                            new CustomDoctorAdapter(activity,
                                    R.layout.iteam_search,
                                    new ArrayList<SuggestedDoctors>
                                            (Arrays.asList(entryRxObj.getSuggestedDoctors())));


                    auto_txt_doctor_id.setAdapter(doctorAdapter);
                    productOneSpinner.setAdapter(productAdapter);
                    productTwoSpinner.setAdapter(productAdapter);


                } else {

                    Toast.makeText(context, "" + entryRxObj.getStatus(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CampaignEntryPageResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });

    }

    void submitCampaignDetails() {

        ApplicationData.hideKeyboard(activity);
        showNotCancalableProgress();

        RequestBody requestFile;
        MultipartBody.Part body;
        if (file != null) {

            requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        } else {
            hideNonCancalableProgressDialog();
            showToastMessage("Please Take a Rx Photo");
            return;

        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SubmitCampaignEntryPageResponse> call =
                apiService.submitCampaignPageEntryResponse(
                        prefsValues.getUser_id().toString(),
                        prefsValues.getToken().toString(),
                        doctor_id,
                        productOneValue,
                        productTwoValue,
                        camp_id, body);

        call.enqueue(new Callback<SubmitCampaignEntryPageResponse>() {
            @Override
            public void onResponse(Call<SubmitCampaignEntryPageResponse> call, Response<SubmitCampaignEntryPageResponse> response) {

                try {

                    SubmitCampaignEntryPageResponse submitCampaignEntryPageResponseObj = response.body();
                    LogMe.e(TAG, "SubmitCampaignEntryPageResponse:" + response.toString());
                    hideNonCancalableProgressDialog();

                    if (submitCampaignEntryPageResponseObj.getStatus().equals("success")) {

                        clearData();

                        showAlertDialog(activity, "Success!",
                                "Successfully upload campaign Rx");
                        imageViewRxPic.setImageBitmap(bitmap);

                    } else {

                        imageViewRxPic.setImageBitmap(bitmap);
                        Toast.makeText(context, "" +
                                submitCampaignEntryPageResponseObj.getStatus(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    hideNonCancalableProgressDialog();
                    Toast.makeText(context, "An Error occurred while submitting Campaign", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<SubmitCampaignEntryPageResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                hideNonCancalableProgressDialog();
                showToastMessage("onFailure: "+t.toString());

            }
        });


    }

    @Override
    public void onClick(View v) {

        if (v == imageViewRxPic) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                askPermission();

            } else {

                onPickImage();

            }
        } else if (v == btnSubmitCampaign) {


            //Toast.makeText(context, "Currently Working with this page ", Toast.LENGTH_SHORT).show();


            if (Connectivity.isConnected(activity)) {

               // if (!checkUserInput()) {
                    submitCampaignDetails();
               // }

            } else {
                showAlertForInternet(activity);
            }

        }
    }

    private void askPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_PICTURES);

                // MY_PERMISSIONS_REQUEST_READ_PICTURES is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void onPickImage() {

        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        chooseImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

    }

    public static void showAlertDialog(final Activity activity, String title, String message) {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(activity);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setTitle(title);
//        builder1.setIcon()
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        // activity.finish();

                    }
                });
//        builder1.setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        ((Activity) LogInActivity.this).finish();
//                    }
//                });
        android.support.v7.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    Uri selectedImageUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_ID) {

            if (resultCode == RESULT_OK) {

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                try {

                    //String output = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
                    // TODO collect picture and show in Imageview
                    imageViewRxPic.setImageBitmap(bitmap);
                    if (bitmap != null) {

                        if (data != null) {

                            selectedImageUri = data.getData();
                            file = new File(getPath(selectedImageUri));
                            LogMe.e("File Image", "data has value " + file.toString());

                        } else {
                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                            // CALL THIS METHOD TO GET THE ACTUAL PATH
                            file = new File(getRealPathFromURI(tempUri));

                            // file = new File(ImagePicker.GLOBAL_URI);
                            LogMe.e("File Image", " data is null : Path" + file.getPath());

                        }
                    }

                } catch (Exception e) {
                    imageViewRxPic.setImageResource(R.drawable.placeholder_imageview);
                    showToastMessage(e.getMessage());
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                imageViewRxPic.setImageResource(R.drawable.placeholder_imageview);
                showToastMessage("CANCELLED");
            }

        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    boolean checkUserInput() {

        //auto_txt_doctor_id.setError(null);
        /*productOneSpinner.setError(null);
        productTwoSpinner.setError(null);*/

        // Store values at the time of the login attempt.
        //String txt = auto_txt_doctor_id.getText().toString();
        /*String proOne = autoCompleteProductOne.getText().toString();
        String proTwo = autoCompleteProductTwo.getText().toString();*/


        boolean cancel = false;
        View focusView = null;


        /*if (TextUtils.isEmpty(txt)) {
            auto_txt_doctor_id.setError(getString(R.string.error_field_required));
            focusView = auto_txt_doctor_id;
            cancel = true;
        }*/ /*else if (TextUtils.isEmpty(proOne)) {
            autoCompleteProductOne.setError(getString(R.string.error_field_required));
            focusView = autoCompleteProductOne;
            cancel = true;
        } else if (TextUtils.isEmpty(proTwo)) {
            autoCompleteProductTwo.setError(getString(R.string.error_field_required));
            focusView = autoCompleteProductTwo;
            cancel = true;
        }*/


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }


    void clearData() {

        auto_txt_doctor_id.setText("");
        doctor_id = "";
        //deleteMethod(getPath(selectedImageUri));


    }

    private void deleteMethod(String file_dj_path) {
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + file_dj_path);
                Toast.makeText(getApplicationContext(),
                        "file Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "file not Deleted", Toast.LENGTH_SHORT).show();
                System.out.println("file not Deleted :" + file_dj_path);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }




}
