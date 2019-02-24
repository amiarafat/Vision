package com.khudrosoft.vision.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khudrosoft.vision.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.khudrosoft.vision.model.ProfileResponse;
import com.khudrosoft.vision.model.UserRow;
import com.khudrosoft.vision.network.ApiClient;
import com.khudrosoft.vision.network.ApiInterface;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.ImagePicker;
import com.khudrosoft.vision.utility.LogMe;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {


    Activity activity;
    private String TAG = "ProfileActivity";

    EditText edtName, edtEmail, edtMobile, edtEmployeeCode, edtUserId, edtRfId, edtDipoId;
    CircleImageView profilePicture;

    Button btnUpdate;
    TextView txtuser_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmployeeCode = findViewById(R.id.edtEmployeeCode);
        edtEmployeeCode.setEnabled(false);
		edtRfId = findViewById(R.id.edtRfId);
		edtDipoId = findViewById(R.id.edtDipoId);
        profilePicture = findViewById(R.id.profilePicture);
        edtUserId = findViewById(R.id.edtUserId);
        edtUserId.setEnabled(false);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);


        txtuser_Name = findViewById(R.id.user_Name);
        getProfileDetails();

        profilePicture.setOnClickListener(this);
        LogMe.i(TAG, "ON CREATE ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogMe.i(TAG, "onResume ");
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

    private void getProfileDetails() {

        ApplicationData.hideKeyboard(activity);
        showProgressDialog();
//        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), et_user_name.getText().toString());
//        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), et_password.getText().toString());
//        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), Constants.ACTION_SIGN_IN);
        LogMe.i("TOKEN: ", "getProfileDetails:" + prefsValues.getToken());


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.getProfileDetails(prefsValues.getUser_id().toString(),
                prefsValues.getToken().toString());

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                hideProgressDialog();
                ProfileResponse profileResponseObj = response.body();
                LogMe.i(TAG, profileResponseObj.toString());

                if (profileResponseObj.getStatus().equals("success")) {

                    UserRow rxUser = profileResponseObj.getUserRow();
                    //LogMe.i(TAG, rxUser.toString());

                    edtName.setText(rxUser.getName());
                    edtEmail.setText(rxUser.getEmail());
                    edtMobile.setText(rxUser.getMobile_no());
                    edtUserId.setText(rxUser.getUser_id());
                    edtEmployeeCode.setText(rxUser.getFfc());
					edtRfId.setText(rxUser.getPin_code());
                    edtDipoId.setText(rxUser.getDepot());

                    txtuser_Name.setText(rxUser.getName());


                    String imageURl = profileResponseObj.getImageUrl();
                    String image_name = rxUser.getImage_name();

                    Picasso.get().load(imageURl + image_name).into(profilePicture);

                } else {

                    Toast.makeText(getApplicationContext(), "" + profileResponseObj.getStatus(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                //   showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });
    }

    File file = null;

    private void updateProfileDetails() {

        ApplicationData.hideKeyboard(activity);
        showProgressDialog();

        LogMe.i("TOKEN: ",
                "getProfileDetails:" +
                        prefsValues.getToken());

        RequestBody requestFile;
        MultipartBody.Part body;
        if (file != null) {

            requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        } else {
            hideNonCancalableProgressDialog();
            showToastMessage("Please Take a Profile Photo");
            return;

        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.updateProfile(
                prefsValues.getUser_id().toString(),
                prefsValues.getToken().toString(),
                edtName.getText().toString(),				
                edtEmployeeCode.getText().toString(),
                edtRfId.getText().toString(),
                edtEmail.getText().toString(),
                edtMobile.getText().toString(),
                edtDipoId.getText().toString(),body);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                hideProgressDialog();
                ProfileResponse profileResponseObj = response.body();
                LogMe.i(TAG, profileResponseObj.toString());

                if (profileResponseObj.getStatus().equals("success")) {

                    Toast.makeText(getApplicationContext(), "" + profileResponseObj.getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "" + profileResponseObj.getStatus(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // Log error here since request failed
                LogMe.e(TAG, t.toString());
                //   showProgress(false);
                hideProgressDialog();
                showToastMessage(t.toString());

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpdate) {

            updateProfileDetails();


        } else if (v == profilePicture) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                askPermission();

            } else {

                onPickImage();

            }
        }
    }

    Uri selectedImageUri = null;
    private static final int MY_PERMISSIONS_REQUEST_READ_PICTURES = 5252;
    private static final int PICK_IMAGE_ID = 234;

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_ID) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                try {

                    //String output = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
                    // TODO collect picture and show in Imageview
                    profilePicture.setImageBitmap(bitmap);
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
                    profilePicture.setImageResource(R.drawable.placeholder_imageview);
                    showToastMessage(e.getMessage());
                    e.printStackTrace();
                }

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

}
