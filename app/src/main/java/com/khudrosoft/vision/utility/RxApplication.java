package com.khudrosoft.vision.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;



/**
 * Created by Shihab on 11/21/2016.
 */

public class RxApplication extends Application {

    private static RxApplication driverApplication;

    private static AppSharedPreference prefsValues;


    public static synchronized AppSharedPreference getPrefsValues() {
        return prefsValues;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);

    }

    @Override
    public void onCreate() {
        super.onCreate();


        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new android.app.Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }


        });

        driverApplication = this;
        //prefsValues = AppSharedPreference.getDefaultPreferences();
    }

    public static synchronized RxApplication getDriverApplication() {
        return driverApplication;
    }

    public void setDriverApplication(RxApplication driverApplication) {
        this.driverApplication = driverApplication;
    }


    public boolean isThisServiceRunning(Class<?> serviceClass) {
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
