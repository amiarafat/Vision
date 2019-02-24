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
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.CustomDoctorAdapter;
import com.khudrosoft.vision.adapter.CustomMaxproAdapter;
import com.khudrosoft.vision.model.MaxProProducts;
import com.khudrosoft.vision.model.MaxproEntryResponse;
import com.khudrosoft.vision.model.SimpleResponse;
import com.khudrosoft.vision.model.SuggestedDoctorResponse;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.Connectivity;
import com.khudrosoft.vision.utility.ImagePicker;
import com.khudrosoft.vision.utility.LogMe;

import static com.khudrosoft.vision.utility.ApplicationData.default_value_max_pro_doctor;

public class MaxproCampaignActivity extends BaseActivity
        implements View.OnClickListener {

    /*date
            doctor
    ekta product field
            image
    tor product field ta dropdown hobe*/
    private static final String TAG = "MaxproCampaignActivity";
    Activity activity;

    AutoCompleteTextView auto_txt_doctor_id;

    TextView txtDate;
    //    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String LONG_DATE_FORMAT = "dd-MM-yyyy";
    ImageView imageViewRxPic;
    Button btnSubmitCampaign;

    private String doctor_id;
    private String productOneValue;
    private static final int MY_PERMISSIONS_REQUEST_READ_PICTURES = 5252;
    private static final int PICK_IMAGE_ID = 234;
    File file = null;

    Spinner productOne;
    private Bitmap bitmap;
    //public static int default_value_max_pro_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxpro_campaign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        txtDate = findViewById(R.id.txtDate);
        txtDate.setEnabled(false);
        auto_txt_doctor_id = findViewById(R.id.auto_txt_doctor_id_max_pro);
        auto_txt_doctor_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Object object = parent.getItemAtPosition(position);
                default_value_max_pro_doctor = position;

                if (object instanceof SuggestedDoctors) {
                    SuggestedDoctors suggestedDoctors = (SuggestedDoctors) object;
                    doctor_id = suggestedDoctors.getDoctor_id();
                    Toast.makeText(getApplicationContext(),
                            doctor_id, Toast.LENGTH_SHORT).show();
                }

                ApplicationData.hideKeyboard(activity);
            }
        });

        productOne = findViewById(R.id.spinnerOne);
        productOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);

                ApplicationData.default_value_max_pro_product = position;
                if (object instanceof MaxProProducts) {
                    MaxProProducts suggestedDoctors = (MaxProProducts) object;
                    productOneValue = suggestedDoctors.getName();
                    Toast.makeText(getApplicationContext(), productOneValue, Toast.LENGTH_SHORT).show();
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

//        if (savedInstanceState != null) {
//
//            default_value_max_pro_product = savedInstanceState.getInt(key_value, 0);
//            productOneSpinner.setSelection(default_value_max_pro_product, true);
//
//        }
        if (Connectivity.isConnected(activity)
                ) {

            getMaxProData();
            getSugggestedDoctorsDetails();

        } else {

            showAlertForInternet(this);
        }

        LogMe.i(TAG, "ON CREATE CALLED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogMe.i(TAG, "ON RESUME CALLED");
        ApplicationData.hideKeyboard(activity);


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

    void getMaxProData() {
        ApplicationData.hideKeyboard(activity);
        showProgressDialog();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MaxproEntryResponse> call = apiService.
                getMaxproData(prefsValues.getUser_id().toString(),
                        prefsValues.getToken().toString());

        call.enqueue(new Callback<MaxproEntryResponse>() {
            @Override
            public void onResponse(Call<MaxproEntryResponse> call, Response<MaxproEntryResponse> response) {
                MaxproEntryResponse entryRxObj = response.body();

                LogMe.i(TAG, "getMaxProData:" + entryRxObj.toString());
                hideProgressDialog();

                if (entryRxObj.getStatus().equals("success")) {


                    CustomMaxproAdapter productAdapter = new CustomMaxproAdapter(activity,
                            new ArrayList<MaxProProducts>(Arrays.asList(entryRxObj.getMaxProProducts())));
                    productAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    productOne.setAdapter(productAdapter);
                    productOne.setSelection(ApplicationData.default_value_max_pro_product);

                } else {

                    Toast.makeText(context, "" + entryRxObj.getStatus(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MaxproEntryResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });

    }

    void submitMaxProDetails() {

        ApplicationData.hideKeyboard(activity);
        showNotCancalableProgress();

        RequestBody requestFile;
        MultipartBody.Part body;
        if (file != null) {

            requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        } else {
            hideProgressDialog();
            showToastMessage("Please Take a Rx Photo");
            return;

        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SimpleResponse> call =
                apiService.submitMaxProEntryResponse(
                        prefsValues.getUser_id().toString(),
                        prefsValues.getToken().toString(),
                        doctor_id,
                        txtDate.getText().toString(),
                        productOneValue,
                        body);

        call.enqueue(new Callback<SimpleResponse>()  {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                try {

                    SimpleResponse SubmitCampaignEntryPageResponseObj = response.body();
                    LogMe.e(TAG, "getCampaignPageEntryResponse:" +
                            SubmitCampaignEntryPageResponseObj.getStatus());

                    hideNonCancalableProgressDialog();

                    if (SubmitCampaignEntryPageResponseObj.getStatus().equals("success")) {

                        clearData();

                        showAlertDialog(activity, "Success!",
                                "Successfully upload campaign Rx");
                        imageViewRxPic.setImageBitmap(bitmap);

                    } else {

                        imageViewRxPic.setImageBitmap(bitmap);
                        Toast.makeText(context, "" + SubmitCampaignEntryPageResponseObj.getStatus(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                    Toast.makeText(context, "An Error occurred while submitting Rx", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                hideNonCancalableProgressDialog();
                showToastMessage(t.toString());

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

                //if (!checkUserInput()) {
                    submitMaxProDetails();
                //}

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
//                    public void onClick(DialogInterface dialog, int campaign_name) {
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
            // Make sure the request was successful
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
                showToastMessage("Cancelled");

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

    boolean checkUserInput() {

        auto_txt_doctor_id.setError(null);
        /*productOneSpinner.setError(null);
        productTwoSpinner.setError(null);*/

        // Store values at the time of the login attempt.
        String txt = auto_txt_doctor_id.getText().toString();
        /*String proOne = autoCompleteProductOne.getText().toString();
        String proTwo = autoCompleteProductTwo.getText().toString();*/


        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(txt)) {
            auto_txt_doctor_id.setError(getString(R.string.error_field_required));
            focusView = auto_txt_doctor_id;
            cancel = true;
        } /*else if (TextUtils.isEmpty(proOne)) {
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

    }

    CustomDoctorAdapter stringArrayAdapterProdyct;

    private void getSugggestedDoctorsDetails() {

        try {
            ApplicationData.hideKeyboard(activity);
            showProgressDialog();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<SuggestedDoctorResponse> call = apiService.getSuggestedDoctors(
                    prefsValues.getUser_id().toString(),
                    prefsValues.getToken().toString());

            call.enqueue(new Callback<SuggestedDoctorResponse>() {
                @Override
                public void onResponse(Call<SuggestedDoctorResponse>
                                               call, Response<SuggestedDoctorResponse> response) {

                    try {
                        SuggestedDoctorResponse entryRxObj = response.body();

                        LogMe.e(TAG, "getSugggestedDoctorsDetails:" + entryRxObj.getStatus());
                        hideProgressDialog();

                        if (entryRxObj.getStatus().equals("success")) {

//                            ArrayList<String> strings = new ArrayList<String>();
//
//                            for (int i = 0; i < entryRxObj.getSuggestedDoctors().length; i++) {
//                                strings.add(entryRxObj.getSuggestedDoctors()[i].getName());
//                                LogMe.i(TAG, "getSugggestedDoctorsDetails" + entryRxObj.getSuggestedDoctors()[i].getName());
//                            }
//
////                            CustomAdapter stringArrayAdapterProdyct =
////                                    new CustomAdapter(activity,
////                                            R.layout.iteam_search, strings);
                            stringArrayAdapterProdyct =
                                    new CustomDoctorAdapter(activity,
                                            R.layout.iteam_search,
                                            new ArrayList<SuggestedDoctors>
                                                    (Arrays.asList(entryRxObj.getSuggestedDoctors())));


                            auto_txt_doctor_id.setAdapter(stringArrayAdapterProdyct);
                            if (default_value_max_pro_doctor != 0) {
                                auto_txt_doctor_id.setSelection(default_value_max_pro_doctor);
                                auto_txt_doctor_id.clearFocus();

                                SuggestedDoctors docObj = entryRxObj.getSuggestedDoctors()[default_value_max_pro_doctor];

                                doctor_id = docObj.getDoctor_id();
                            }
                            stringArrayAdapterProdyct.notifyDataSetChanged();


                        } else {

                            Toast.makeText(context, "" + entryRxObj.getStatus(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, " Error !! in getSugggestedDoctorsDetails", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<SuggestedDoctorResponse> call, Throwable t) {
                    // Log error here since request failed
                    LogMe.e(TAG, t.toString());
                    hideProgressDialog();
                    showToastMessage(t.toString());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    final String key_value = "product_one_index";

    @Override
    public void onSaveInstanceState(Bundle outState,
                                    PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt(key_value,
                productOne.getSelectedItemPosition());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogMe.i(TAG, "ON DESTROY");
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
}
