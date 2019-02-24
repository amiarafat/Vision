package com.khudrosoft.vision.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreference {

    private static AppSharedPreference defaultPreferences = new AppSharedPreference();

    private SharedPreferences mPrefs;
    //    private String app_user_id = "id";
    private String driver_user_name = "user_name";
    private String contactNo = "contactNo";
    private String password = "password";
    private String is_registered = "is_registered";

    private String is_logged_in = "is_loggedin";
    private String driver_full_name = "name";
    private String driver_email = "driver_email";
    private String driver_license_number = "driver_license";
    private String is_achieve_last_lat_long = "is_get_last_lat_long";
    private String cab_type = "cab_type";
    private String cab_type_number = "";
    private String willSignIN = "will_sign_in";
    private String last_latitude = "last_lat";
    private String last_longitude = "last_long";
    private String picture_name = "picture_name";
    private String driverMode = "driver_mode";
    private String driver_balance = "driver_balance";
    private String driver_id_card_number = "driver_card_number";
    private String sticky_notification = "sticky_notification";
    private String token = "token";
    private String user_id = "user_id";


//    private String driver_phone_number = "phone_number";

    //
    private String firebaseRegistratoinID = "firebase_reg_id";

    Context context = null;
    Activity activity;
    SharedPreferences.Editor editor;

    public AppSharedPreference(Context context) {

        this.context = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this.context);

    }

    public AppSharedPreference() {
        this.mPrefs = RxApplication.getDriverApplication().
                getSharedPreferences(ApplicationData.APP_NAME, Context.MODE_PRIVATE);
    }


    public AppSharedPreference(Context context, String name, int mode) {

        this.context = context;
        mPrefs = this.context.getSharedPreferences(name, mode);
        //editor = mPrefs.edit();

    }

    public AppSharedPreference(Activity activity) {

        this.activity = activity;
        mPrefs = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public static AppSharedPreference getDefaultPreferences() {
        return defaultPreferences;
    }

    public SharedPreferences getPrefs() {
        return mPrefs;
    }


    public String getDriver_user_name() {
        return mPrefs.getString(this.driver_user_name, "");
    }

    public void setDriver_user_name(String value) {
        mPrefs.edit().putString(this.driver_user_name, value).commit();
    }

    public String getContactNo() {
        return mPrefs.getString(this.contactNo, "");
    }

    public void setContactNo(String value) {
        mPrefs.edit().putString(this.contactNo, value).commit();
    }

    public void clearPreference(String value) {

        SharedPreferences settings = context.getSharedPreferences(value, Context.MODE_PRIVATE);
        mPrefs.edit().clear().commit();
//        SharedPreferences.Editor.clear();
    }

    public void clearPreference() {

        mPrefs.edit().clear().commit();

    }

    public void clearValue(String key) {

        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        preferences.getAll().clear();
    }

    public void clearValue(String key,String preference_name) {

        SharedPreferences preferences = activity.getSharedPreferences(preference_name,Context.MODE_PRIVATE);
        preferences.getAll().clear();
    }

    public void clear() {
//        SharedPreferences prefs; // here you get your prefrences by either of two methods
//        SharedPreferences.Editor editor = getPrefs().edit();
//        editor.clear();
//        editor.commit();

        SharedPreferences preferences = context.getSharedPreferences("chat_me", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
/**/
    }

    public String getPassword() {
        return mPrefs.getString(this.password, "");
    }

    public void setPassword(String password) {
        mPrefs.edit().putString(this.password, password).commit();
    }

    public boolean is_registered() {
        return mPrefs.getBoolean(this.is_registered, false);
    }

    public void setIs_registered(boolean is_registered) {
        mPrefs.edit().putBoolean(this.is_registered, is_registered).commit();
    }

    public boolean getIs_logged_in() {
        return mPrefs.getBoolean(this.is_logged_in, false);
    }

    public void setIs_logged_in(boolean is_logged_in) {
        mPrefs.edit().putBoolean(this.is_logged_in, is_logged_in).commit();
    }

    public String getUser_id() {
        return mPrefs.getString(this.user_id, "");

    }

    public void setUser_id(String user_id) {
        mPrefs.edit().putString(this.user_id, user_id).commit();
    }

    public String getDriver_full_name() {
        return mPrefs.getString(this.driver_full_name, "");
    }

    public void setDriver_full_name(String driver_full_name) {
        mPrefs.edit().putString(this.driver_full_name, driver_full_name).commit();
    }

    public String getDriver_email() {
        return mPrefs.getString(this.driver_email, "");
    }

    public void setDriver_email(String driver_email) {
        mPrefs.edit().putString(this.driver_email, driver_email).commit();
    }

    public String getDriver_license_number() {
        return mPrefs.getString(this.driver_license_number, "");
    }

    public void setDriver_license_number(String driver_license_number) {
        mPrefs.edit().putString(this.driver_license_number, driver_license_number).commit();
    }

    public String getFirebaseRegistratoinID() {
        return mPrefs.getString(this.firebaseRegistratoinID, "");
    }

    public void setFirebaseRegistratoinID(String firebaseRegistratoinID) {
        mPrefs.edit().putString(this.firebaseRegistratoinID, firebaseRegistratoinID).commit();
    }

    public Boolean is_achieve_last_lat_long() {
        return mPrefs.getBoolean(this.is_achieve_last_lat_long, false);
    }

    public void setIs_achieve_last_lat_long(Boolean value) {
        mPrefs.edit().putBoolean(this.is_achieve_last_lat_long, value).commit();

    }


    public String getCab_type() {
        return mPrefs.getString(this.cab_type, "");
    }

    public void setCab_type(String value) {
        mPrefs.edit().putString(this.cab_type, value).commit();
    }

    public int getCab_type_number() {
        return mPrefs.getInt(this.cab_type_number, 0);
    }

    public void setCab_type_number(int cab_type_number) {
        mPrefs.edit().putInt(this.cab_type_number, cab_type_number).commit();
    }

    public Boolean getWillSignIN() {
        return mPrefs.getBoolean(this.willSignIN, false);
    }

    public void setWillSignIN(boolean willSignIN) {
        mPrefs.edit().putBoolean(this.willSignIN, willSignIN).commit();
    }

    public String getLast_latitude() {
        return mPrefs.getString(this.last_latitude, "");
    }

    public void setLast_latitude(String last_latitude) {
        mPrefs.edit().putString(this.last_latitude, last_latitude).commit();

    }

    public String getLast_longitude() {

        return mPrefs.getString(this.last_longitude, "");

    }

    public void setLast_longitude(String last_longitude) {

        mPrefs.edit().putString(this.last_longitude, last_longitude).commit();

    }

    public String getPicture_name() {
        return mPrefs.getString(this.picture_name, "");
    }

    public void setPicture_name(String picture_name) {
        mPrefs.edit().putString(this.picture_name,
                picture_name).commit();
    }

    public int getDriverMode() {
        return mPrefs.getInt(this.driverMode, 0);
    }

    public void setDriverMode(int driverMode) {
        mPrefs.edit().putInt(this.driverMode,
                driverMode).commit();
    }

    public String getDriver_balance() {
        return mPrefs.getString(this.driver_balance, "");
    }

    public void setDriver_balance(String driver_balance) {
        mPrefs.edit().putString(this.driver_balance,
                driver_balance).commit();
    }

    public String getDriver_id_card_number() {
        return mPrefs.getString(this.driver_id_card_number, "");
    }

    public void setDriver_id_card_number(String driver_id_card_number) {
        mPrefs.edit().putString(this.driver_id_card_number,
                driver_id_card_number).commit();
    }

    public boolean getSticky_notification() {
        return mPrefs.getBoolean(this.sticky_notification, true);
    }

    public void setSticky_notification(Boolean sticky_notification) {
        this.mPrefs.edit().putBoolean(this.sticky_notification, sticky_notification).commit();
    }

    public String getToken() {
        return mPrefs.getString(this.token, "");
    }

    public void setToken(String token) {
        this.mPrefs.edit().putString(this.token, token).commit();
    }
}