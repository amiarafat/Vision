package com.khudrosoft.vision.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


import com.khudrosoft.vision.R;
import com.khudrosoft.vision.utility.ApplicationData;
import com.khudrosoft.vision.utility.LogMe;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_TIME = 3000; // splash screen delay time
    //AppSharedPreference prefsValues;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove date bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO CAN CHECK WHETHER USER IS ALREADY REGISTERED OR NOT USING PREFERENCE

        //prefsValues = new AppSharedPreference(activity, ApplicationData.APP_NAME, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (!prefsValues.getToken().isEmpty()) {

                    LogMe.i("TOKEN: ", prefsValues.getToken());
                    ApplicationData.goToHomeActivity(activity);
                    activity.finish();

                } else {

                    ApplicationData.goToLogInActivity(activity);

                }

            }
        }, SPLASH_DISPLAY_TIME);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

}
