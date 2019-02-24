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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.khudrosoft.vision.R;
import com.khudrosoft.vision.utility.ImagePicker;
import com.khudrosoft.vision.utility.LogMe;

public class SampleActivity extends BaseActivity implements
        View.OnClickListener {

    Activity activity;
    private Button buttonUpload, buttonDelete;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;
        buttonUpload = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        imageView = findViewById(R.id.imageView);

        buttonUpload.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == buttonDelete) {

            try {

                deleteMethod(getPath(selectedImageUri));

            } catch (Exception e) {

                showToastMessage(e.getMessage());
            }


        } else if (v == buttonUpload) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                askPermission();

            } else {

                onPickImage();

            }
        }
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

    private static final int PICK_IMAGE_ID = 234;
    private static final int MY_PERMISSIONS_REQUEST_READ_PICTURES = 5252;

    File file = null;

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

    Uri selectedImageUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == PICK_IMAGE_ID) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {

                    Bitmap bitmap = ImagePicker.
                            getImageFromResult(this, resultCode, data);
                    try {

                        //String output = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
                        // TODO collect picture and show in Imageview
                        imageView.setImageBitmap(bitmap);
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
                        imageView.setImageResource(R.drawable.placeholder_imageview);
                        showToastMessage(e.getMessage());
                        e.printStackTrace();
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    showToastMessage("Cancelled...");

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            showToastMessage(e.getMessage());
            imageView.setImageResource(R.drawable.placeholder_imageview);

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

}
