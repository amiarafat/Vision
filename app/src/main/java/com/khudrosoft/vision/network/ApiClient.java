package com.khudrosoft.vision.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amir on 11/26/2016.
 */

public class ApiClient {

    //    http://u-cabs.com/ksa
//    public static final String BASE_URL = "http://rx-calculator.xyz";
        public static final String BASE_URL = "http://renata-vision.xyz";
//    public static final String BASE_URL = "http://u-cabs.com";
//    public static final String BASE_URL = "http://u-cabs.com";
    //    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}