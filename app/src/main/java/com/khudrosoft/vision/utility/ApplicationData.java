package com.khudrosoft.vision.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.khudrosoft.vision.activity.AboutActivity;
import com.khudrosoft.vision.activity.BirthdayActivity;
import com.khudrosoft.vision.activity.ChangePasswordActivity;
import com.khudrosoft.vision.activity.HelpActivity;
import com.khudrosoft.vision.activity.HomeActivity;
import com.khudrosoft.vision.activity.LogInActivity;
import com.khudrosoft.vision.activity.NoticeActivity;
import com.khudrosoft.vision.activity.ProfileActivity;
import com.khudrosoft.vision.activity.TargetActivty;

public class ApplicationData {

    /*number - 01613548376
    email - support@rx-calculator.xyz
    web http://rx-calculator.xyz*/

    public static final String TRANSACTION_ID = "Transaction Id: ";
    public static final String HELP_CALL = "01613548376";
    public static final String HELP_EMAIL = "support@renata-vision.xyz";
    public static final String HELP_WEB = "http://renata-vision.xyz";
    public static final String MOVE_TO_TARGET = "TARGET";
    public static final int MOVE_TO_TARGET_VALUE = 100;
    public static final String CAMPAIGN_NAME = "CAMPAIGN_NAME";


    public static int default_value_max_pro_product = 0;
    public static int default_value_max_pro_doctor = 0;
    public static int default_value_submit_rx = 0;

    public static final String PICK_FROM = "Pick From: ";
    public static final String DESTINATION = "Destination: ";
    // GENERAL PRODUCT DETAILS
    public static String APP_NAME = "rx_calculator";

    public static boolean STATUS_REGISTRATION = false;
    public static boolean STATUS_LOGGED_IN = false;

    // ucabs gmail. diye khola
//    https://developers.google.com/maps/documentation/distance-matrix/
//    AIzaSyDfqAjHAfmVrU3rqaeQPr2e2bzYwSgGFRM
    public static final String DIRECTION_API_KEY_ = "AIzaSyBgpHWY36_hd9kVxnxOUw2g0acQjF6hmdA";

    /*Driver key : android MAP
    AIzaSyCWebef6TtGNe-YrkYGniSJ22wYRl6McFA

    Distance Matrix API KEY
    AIzaSyDGac5JSmydo6rFC67pHu2urq7JaX56K4w

    Directions API
    AIzaSyA_3_BAoDR1YSlhRfKC99rcV-f6cwSK2gY*/
    public static String ROUTE_API_KEY = "AIzaSyA_3_BAoDR1YSlhRfKC99rcV-f6cwSK2gY";
    public static String MAP_API_KEY = "AIzaSyCWebef6TtGNe-YrkYGniSJ22wYRl6McFA";


    public static String TAXI_BASE_URL = "http://dev.riverbelt.com/taxiapi/api?";
    public static String ACTION_DRIVER_SIGN_UP = "driver_signup";
    public static String ACTION_DRIVER_LOG_IN = "driver_login";
    public static String ACTION_DRIVER_PROFILE_UPDATE = "driver_profile_update";
    public static String ACTION_DRIVER_UPDATE_LOCATION = "update_driver_location";
    public static String ACTION_DRIVER_MODE = "update_driver_mode";


    public static String ACTION = "action";
    public static String PUSH_NOTIFICATION_TRIP_REQUEST = "trip_request";
    public static String PUSH_NOTIFICATION_TRIP_FINISHED = "trip_finish";

    public static final String TRIP_CONFIRM = "Trip Request";
    public static final String TRIP_FINISHED = "Trip Finished";
    public static final String DRIVER_ARRIVED = "Driver Arrived";
    public static final String DRIVER_REACHED_AT_DESTINATION = "Driver Arrived Destination";
    public static final String DRIVER_PICKED_PASSENGER = "Picked";
    public static final String DRIVER_CASH_RECEIVED = "Payment Completed";

    public static final String IMAGE_FILE = "";


//    "message": "Trip Finished",

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");

    public static String spilitFirstString(String string) {

        String[] namesList = string.split("\\s+");

        return namesList[0];

    }

//    public static String getPostResponseFromServer(String url, String json) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(ApplicationData.JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }

    /*public static String spilitStringFirst(String string) {

        String[] namesList = string.split("\\.");
        return namesList[0];

    }*/

    public static String spilitStringSecond(String string) {

        String[] namesList = string.split("\\.");
        return namesList[1];

    }

    public static String farenhiteToCelcius(String farenhite) {

        float value = Float.parseFloat(farenhite);
        float celcius = new Float(.2f);
        celcius = ((value - 32) * 5) / 9;
        Log.e("AppData f2c", "" + celcius);
        return String.format("%.01f", celcius);

    }

    public static String getDateName(String dates) {
        String goal = null;
        try {

            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inFormat.parse(dates);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            goal = outFormat.format(date);

        } catch (Exception e) {

            e.printStackTrace();
        }


        return goal;

    }

    public static String getDateNameFromMiliSecond(String milliSeconds) throws ParseException {

        String dateName = null;

        /*SimpleDateFormat inFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
        String dateString = inFormat.format(milliSeconds);

        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        goal = outFormat.format(dateString);*/

        //String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (milliSeconds));


        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(milliSeconds));  //here your time in miliseconds
        int value = cl.get(Calendar.DAY_OF_WEEK);

        switch (value) {

            case 1:
                return dateName = "Sun";
            case 2:
                return dateName = "Mon";
            case 3:
                return dateName = "Tue";
            case 4:
                return dateName = "Wed";
            case 5:
                return dateName = "Thu";
            case 6:
                return dateName = "Fri";
            case 7:
                return dateName = "Sat";

        }
        return dateName;

    }


    /*SimpleDateFormat format = new SimpleDateFormat("MMddyyHHmmss");
    Date date = format.parse("022310141505");
    */
    public static String convertDate(String dateInMilliseconds, String dateFormat) {

        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public static void goToLogInActivity(Activity activity) {

        Intent intent = new Intent(activity, LogInActivity.class);
        activity.startActivity(intent);
        activity.finish();

    }


    public static void goToBirthDayActivity(Activity activity) {

        Intent intent = new Intent(activity, BirthdayActivity.class);
        activity.startActivity(intent);

    }


    public static void goToHelpActivity(Activity activity) {

        Intent intent = new Intent(activity, HelpActivity.class);
        activity.startActivity(intent);

    }

    public static void gotoAboutActivity(Activity activity) {

        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);

    }

//    public static void goToChangePasswordActivity(Activity activity) {
//
//        Intent intent = new Intent(activity, ChangePasswordActivity.class);
//        activity.startActivity(intent);
//
//    }


    public static void goToUpdateProfileActivity(Activity activity) {

        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);

    }

   /* public static void goToChangeModeActivity(Activity activity) {

        Intent intent = new Intent(activity, ChangeModeActivity.class);
        activity.startActivity(intent);
        activity.finish();

    }*/

    public static void goToSignUpActivity(Activity activity) {

//        Intent intent = new Intent(activity, SignUpActivity.class);
//        activity.startActivity(intent);
//        activity.finish();

    }

    public static void goToHomeActivity(Activity activity) {

        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        //activity.finish();

    }

    public static void goToForgotPassActivity(Activity activity) {

//        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
//        activity.startActivity(intent);
//        activity.finish();

    }
    //	Library We use
    //	Toast: https://github.com/JohnPersano/Supertoasts/wiki/SuperActivityToast


    /* From Activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*
       From Fragment
    */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


//    public static boolean validateEmail(String email) {
//
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }


    public static void showAlertForInternet(Activity activity) {

        android.support.v7.app.AlertDialog.Builder builder1;
        builder1 = new android.support.v7.app.AlertDialog.Builder(activity);
        builder1.setMessage("Please check your internet connection");
        builder1.setCancelable(false);
        builder1.setTitle("Network Error!");
//        builder1.setIcon()
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    public static void showAlertDialog(Activity activity, String title, String message) {

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

    /**
     * return distance in km
     */
    public static double DISTANCE_OF_TWO_LOCATION_IN_KM(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String getLocationAddress(Context context, double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        String address = null;
        try {

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {

                address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();

                Log.e("getLocationAddress", "" + address + " " + state);

            }

        } catch (Exception e) {

            return null;

        }

        return address;

    }

    /**
     * Find Midpoint bettween two LatLong Object
     */
//    public static LatLng midPoint(double sourceLat, double sourceLong, double destinationLat, double destinatonLong) {
//
//        return new LatLng((sourceLat + destinationLat) / 2, (sourceLong + destinatonLong) / 2);
//
//    }

    /**
     * Calculate angle between middle point and any other marker position.
     */
    public static float angleBteweenCoordinate(double lat1, double long1, double lat2,
                                               double long2) {

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng;

        return (float) brng;

    }

    public static boolean isPasswordMatching(String password, String confirmPassword) {
        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");

            return false;
        }

        return true;
    }

    public static void goToNoticeList(Activity activity) {
        Intent intent = new Intent(activity, NoticeActivity.class);
        activity.startActivity(intent);
    }

    public static void goToUpPassActivity(HomeActivity homeActivity) {
        Intent intent = new Intent(homeActivity, ChangePasswordActivity.class);
        homeActivity.startActivity(intent);
    }

    public static void goTargetActivity(HomeActivity homeActivity) {
        Intent intent = new Intent(homeActivity, TargetActivty.class);
        homeActivity.startActivity(intent);
    }


    /*protected boolean checkPlayServices() {
        final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST);
                if (dialog != null) {
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (ConnectionResult.SERVICE_INVALID == resultCode) activity().finish();
                        }
                    });
                    return false;
                }
            }
            new CSAlertDialog(this).show("Google Play Services Error",
                    "This device is not supported for required Goole Play Services", "OK", new Call() {
                        public void onCall(Object value) {
                            activity().finish();
                        }
                    });
            return false;
        }
        return true;
    }*/

    // Checking if Google Play Services Available or not
//    public static boolean isGooglePlayServicesAvailable(Activity activity) {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(activity);
//        if (result != ConnectionResult.SUCCESS) {
//            if (googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(activity, result,
//                        0).show();
//            }
//            return false;
//        }
//        return true;
//    }

//    public static void gotToPaymentActivity(Activity homeActivity) {
//
//        Intent intent = new Intent(homeActivity, PaymentActivity.class);
//        homeActivity.startActivity(intent);
//
//    }



    /*public static void startDual(Context context) {

        EndCallListener callListener = new EndCallListener();
        TelephonyManager mTM = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);

    }*/


}
