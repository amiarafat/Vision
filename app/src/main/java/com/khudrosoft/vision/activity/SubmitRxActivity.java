package com.khudrosoft.vision.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.adapter.CustomAdapter;
import com.khudrosoft.vision.adapter.CustomDoctorAdapter;
import com.khudrosoft.vision.model.SuggestedDoctorResponse;
import com.khudrosoft.vision.model.SuggestedDoctors;
import com.khudrosoft.vision.model.SuggestedProductResponse;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.network.RxSubmitResponse;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitRxActivity extends BaseActivity implements View.OnClickListener
//        , IPickResult
{

    AutoCompleteTextView spinnerProductOne, spinnerProductTwo,
            spinnerProductThree, spinnerProductFour, spinnerProductFive;
    AutoCompleteTextView autoComTxtDoctor;

    TextView editTextDate;


    // 格式：年－月－日
//    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String LONG_DATE_FORMAT = "dd-MM-yyyy";
    private Activity activity;
    private String TAG = "SubmitRxActivity";
    ImageView imageViewRxPic;
    Button btnSubmitRx;
    RelativeLayout rlImage;

    TextView imageHint;
    AutoCompleteTextView search;
    private Bitmap bitmap;

    Bitmap latestBitmap;
    ImageView ivZoom;

    void clearData() {

        spinnerProductOne.setText("");
        spinnerProductTwo.setText("");
        spinnerProductThree.setText("");
        spinnerProductFour.setText("");
        spinnerProductFive.setText("");
        autoComTxtDoctor.setText("");

        doctorsId = "";
        //deleteMethod(getPath(selectedImageUri));

    }

    String doctorsId = "";
    private static final int MY_PERMISSIONS_REQUEST_READ_PICTURES = 5252;
    private static final int PICK_IMAGE_ID = 234;
    File file = null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumit_rx);
        getWindow().addFlags
                (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        editTextDate = findViewById(R.id.txtDate);
        editTextDate.setEnabled(false);

        autoComTxtDoctor = findViewById(R.id.doctor_id);


        autoComTxtDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object object = parent.getItemAtPosition(position);

                if (object instanceof SuggestedDoctors) {
                    SuggestedDoctors suggestedDoctors = (SuggestedDoctors) object;
                    doctorsId = suggestedDoctors.getDoctor_id();
                    //Toast.makeText(getApplicationContext(), doctorsId, Toast.LENGTH_SHORT).show();
                }

                ApplicationData.hideKeyboard(activity);

            }
        });

        spinnerProductOne = findViewById(R.id.spinnerOne);
        spinnerProductTwo = findViewById(R.id.spinnerTwo);
        spinnerProductThree = findViewById(R.id.spinnerThree);
        spinnerProductFour = findViewById(R.id.spinnerFour);
        spinnerProductFive = findViewById(R.id.spinnerFive);

        imageViewRxPic = findViewById(R.id.imageViewRxPic);
        //imageViewChoosePicture = findViewById(R.id.imageViewChoosePicture);
        //imageViewChoosePicture.setOnClickListener(this);

        btnSubmitRx = findViewById(R.id.btnSubmitRx);
        btnSubmitRx.setOnClickListener(this);

        rlImage = findViewById(R.id.rlPhoto);
        rlImage.setOnClickListener(this);
        //imageHint = findViewById(R.id.imageHint);

        //Arafat
        ivZoom =findViewById(R.id.ivZoom);
        ivZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showZoom();
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date());
        editTextDate.setText(currentDateandTime);

        /*search = findViewById(R.id.search);

        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.iteam_search, getData());

        search.setAdapter(adapter);
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        bitmap = ((BitmapDrawable) imageViewRxPic.getDrawable()).getBitmap();


        if (Connectivity.isConnected(activity)) {

            getSugggestedProductDetails();
            getSugggestedDoctorsDetails();

        } else {

            showAlertForInternet(this);
        }
        //spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("Fashion Men");
        dataList.add("Fashion Women");
        dataList.add("Baby");
        dataList.add("Kids");
        dataList.add("Electronics");
        dataList.add("Appliance");
        dataList.add("Travel");
        dataList.add("Bags");
        dataList.add("FootWear");
        dataList.add("Jewellery");
        dataList.add("Sports");
        dataList.add("Electrical");
        dataList.add("Sports Kids");
        return dataList;
    }

    private void getSugggestedProductDetails() {

        try {
            ApplicationData.hideKeyboard(activity);
            showProgressDialog();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<SuggestedProductResponse> call = apiService.getSuggestedProducts(
                    prefsValues.getUser_id().toString(),
                    prefsValues.getToken().toString());

            call.enqueue(new Callback<SuggestedProductResponse>() {
                @Override
                public void onResponse(Call<SuggestedProductResponse>
                                               call, Response<SuggestedProductResponse> response) {

                    try {
                        SuggestedProductResponse entryRxObj = response.body();

                        LogMe.e(TAG, "getRxEntryDetails:" + entryRxObj.getStatus());
                        hideProgressDialog();

                        if (entryRxObj.getStatus().equals("success")) {

                            ArrayList<String> strings = new ArrayList<String>();
                            //ArrayList<String> stringsProduct = new ArrayList<String>();

                            for (int i = 0; i < entryRxObj.getProducts().length; i++) {
                                strings.add(entryRxObj.getProducts()[i].getName());
                                LogMe.i(TAG, "" + entryRxObj.getProducts()[i].getName());
                            }
                            //new ArrayList<>(Arrays.asList(entryRxObj.getSuggestedDoctors()));

                            CustomAdapter stringArrayAdapterProdyct =
                                    new CustomAdapter(activity,
                                            R.layout.iteam_search, strings);

                            spinnerProductOne.setAdapter(stringArrayAdapterProdyct);
                            spinnerProductTwo.setAdapter(stringArrayAdapterProdyct);
                            spinnerProductThree.setAdapter(stringArrayAdapterProdyct);
                            spinnerProductFour.setAdapter(stringArrayAdapterProdyct);
                            spinnerProductFive.setAdapter(stringArrayAdapterProdyct);


                        } else {

                            Toast.makeText(context, "" + entryRxObj.getStatus(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<SuggestedProductResponse> call, Throwable t) {
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

                            /*ArrayList<String> strings = new ArrayList<String>();

                            for (int i = 0; i < entryRxObj.getSuggestedDoctors().length; i++) {
                                strings.add(entryRxObj.getSuggestedDoctors()[i].getName());
                                LogMe.i(TAG, "" + entryRxObj.getSuggestedDoctors()[i].getName());
                            }*/

//                            CustomAdapter stringArrayAdapterProdyct =
//                                    new CustomAdapter(activity,
//                                            R.layout.iteam_search, string

                            CustomDoctorAdapter stringArrayAdapterProdyct =
                                    new CustomDoctorAdapter(activity,
                                            R.layout.iteam_search,
                                            new ArrayList<SuggestedDoctors>
                                                    (Arrays.asList(entryRxObj.getSuggestedDoctors())));

                            autoComTxtDoctor.setAdapter(stringArrayAdapterProdyct);


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


    private void submitRxEntryDetails() {
        // todo : just switched off

        //if (!checkUserInput()) {
        try {
            showNotCancalableProgress();
            ApplicationData.hideKeyboard(activity);

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
            Call<RxSubmitResponse> call = apiService.submitRxEntryDetails(
                    prefsValues.getUser_id().toString(),
                    prefsValues.getToken().toString(),
                    doctorsId,
                    editTextDate.getText().toString(),
                    spinnerProductOne.getText().toString(),
                    spinnerProductTwo.getText().toString(),
                    spinnerProductThree.getText().toString(),
                    spinnerProductFour.getText().toString(),
                    spinnerProductFive.getText().toString(),
                    body
            );

            call.enqueue(new Callback<RxSubmitResponse>() {
                @Override
                public void onResponse(Call<RxSubmitResponse> call, Response<RxSubmitResponse> response) {

                    try {
                        hideNonCancalableProgressDialog();
                        RxSubmitResponse entryRxObj = response.body();

                        LogMe.i(TAG, "submitRxEntryDetails:" + entryRxObj.toString());

                        //Resources resources = ResourcesCompat.getDrawable();


                        if (entryRxObj.getStatus().equals("success")) {
                            clearData();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            showMessage("Rx Submitted Successfully", "Success!");
                            imageViewRxPic.setImageBitmap(bitmap);

                        } else {
                            showMessage("Rx not Submitted Successfully. " + entryRxObj.getStatus(), "Failed!");
                            //imageViewRxPic.setImageDrawable(resources.getDrawable(R.drawable.placeholder_imageview));

                            imageViewRxPic.setImageBitmap(bitmap);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideNonCancalableProgressDialog();
                        showMessage("Rx not Submitted Successfully. " +
                                "An Exception occured." + e.getMessage(), "Failed!");
                    }

                }

                @Override
                public void onFailure(Call<RxSubmitResponse> call, Throwable t) {
                    // Log error here since request failed
                    LogMe.e(TAG, t.toString());
                    hideNonCancalableProgressDialog();
                    showToastMessage(t.toString());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // }


    }

    int value = R.drawable.placeholder_imageview;

    @Override
    public void onClick(View v) {

        if (v == btnSubmitRx) {

            if (Connectivity.isConnected(activity)) {

                submitRxEntryDetails();

            } else {

                showAlertForInternet(activity);

            }

        } else if (v == rlImage) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                askPermission();

            } else {

                onPickImage();

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PICTURES: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onPickImage();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    showToastMessage("Accept permission to pick picture");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void onPickImage() {

        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        chooseImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

    }
    //UPDATED!

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

    Uri selectedImageUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICK_IMAGE_ID) {

            try {
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    latestBitmap = bitmap;
                    ivZoom.setVisibility(View.VISIBLE);

                    //String output = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
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
                            LogMe.e("File Image", " data has value : Path: " + file.getPath());

                        }
                    }
                } else if (resultCode == RESULT_CANCELED) {

                    imageViewRxPic.setImageBitmap(bitmap);

                }
            } catch (Exception e) {
                showToastMessage("" + e.getMessage());
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


    void showMessage(String message, String title) {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(activity);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setTitle(title);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //imageViewRxPic.setImageBitmap(null);
                        dialog.dismiss();
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


    public void deleteImage(String fileNameWithPath) {
        String file_dj_path = Environment.getExternalStorageDirectory() + fileNameWithPath;
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
        }
    }

    private void deleteMethod(String file_dj_path) {
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + file_dj_path);
                /*Toast.makeText(getApplicationContext(),
                        "file Deleted", Toast.LENGTH_SHORT).show();*/
            } else {
                /*Toast.makeText(getApplicationContext(),
                        "file not Deleted", Toast.LENGTH_SHORT).show();*/
                System.out.println("file not Deleted :" + file_dj_path);
            }
        }
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    boolean checkUserInput() {

        //autoComTxtDoctor.setError(null);
        spinnerProductOne.setError(null);
        spinnerProductTwo.setError(null);
        spinnerProductThree.setError(null);
        spinnerProductFour.setError(null);
        spinnerProductFive.setError(null);

        // Store values at the time of the login attempt.
        //String txt = autoComTxtDoctor.getText().toString();
        String proOne = spinnerProductOne.getText().toString();
        String proTwo = spinnerProductTwo.getText().toString();
        String proThree = spinnerProductThree.getText().toString();
        String proFour = spinnerProductFour.getText().toString();
        String proFive = spinnerProductFive.getText().toString();


        boolean cancel = false;
        View focusView = null;

      /*  // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }*/


        /*if (TextUtils.isEmpty(txt)) {
            autoComTxtDoctor.setError(getString(R.string.error_field_required));
            focusView = autoComTxtDoctor;
            cancel = true;
        } else*/
        if (TextUtils.isEmpty(proOne)) {
            spinnerProductOne.setError(getString(R.string.error_field_required));
            focusView = spinnerProductOne;
            cancel = true;
        } else if (TextUtils.isEmpty(proTwo)) {
            spinnerProductTwo.setError(getString(R.string.error_field_required));
            focusView = spinnerProductTwo;
            cancel = true;
        } else if (TextUtils.isEmpty(proThree)) {
            spinnerProductThree.setError(getString(R.string.error_field_required));
            focusView = spinnerProductThree;
            cancel = true;
        } else if (TextUtils.isEmpty(proFour)) {
            spinnerProductFour.setError(getString(R.string.error_field_required));
            focusView = spinnerProductFour;
            cancel = true;
        } else if (TextUtils.isEmpty(proFive)) {
            spinnerProductFive.setError(getString(R.string.error_field_required));
            focusView = spinnerProductFive;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }

    /* @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            // getImageView().setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }*/

//    PickImageDialog.build(new PickSetup()).show(this);


    private void showZoom(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_image_zoom_layout, null);
        dialogBuilder.setView(dialogView);

        ImageView ivCloseDialog = dialogView.findViewById(R.id.ivCloseDialog);


        ImageView iv = dialogView.findViewById(R.id.myZoomageView);
        iv.setImageBitmap(latestBitmap);
        final AlertDialog alertDialog = dialogBuilder.create();

        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}
