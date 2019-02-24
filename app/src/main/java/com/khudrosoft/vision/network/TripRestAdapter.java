package com.khudrosoft.vision.network;

/**
 * Created by ajaythakur on 8/23/16.
 */

import android.util.Log;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRestAdapter {

    protected final String TAG = getClass().getSimpleName();
    protected Retrofit mRestAdapter;
    protected TripAPI mApi;
    static final String GOOGLE_DIRECTION_API_URL = "https://maps.googleapis.com/maps/";
    static final String GOOGLE_DISTANCE_API_URL = "https://maps.googleapis.com/maps/";
    static final String OPEN_WEATHER_API = "51337ba29f38cb7a5664cda04d84f4cd";

    public TripRestAdapter() {
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(GOOGLE_DIRECTION_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRestAdapter.create(TripAPI.class); // create the interface
        Log.d(TAG, "TaxiRestAdapter -- created");
    }

    public TripRestAdapter(String type) {
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(GOOGLE_DIRECTION_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRestAdapter.create(TripAPI.class); // create the interface
        Log.d(TAG, "TaxiRestAdapter -- created");
    }


    }


