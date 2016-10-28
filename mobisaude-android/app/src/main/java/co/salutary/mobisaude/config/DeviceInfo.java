package co.salutary.mobisaude.config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import co.salutary.mobisaude.util.ConnectivityUtils;

public class DeviceInfo {

    private static final String TAG = new Object(){}.getClass().getName();

    // login variables
    private static boolean isLoggedIn = false;
    private static LoginType loginType = null;
    public enum LoginType {
        GOOGLE, FACEBOOK, EMAIL_PWD
    }
    private static GoogleApiClient mGoogleApiClient;

    // location variables
    public static double lastLatitude;
    public static double lastLongitude;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 0;

    // authorization variables
    public static boolean hasToken = false;

    // general variables
    public static String statusMessage = "";
    public static final int NUM_GPS_ATTEMPTS = 15;
    public static final long SLEEP_TIME = 1000;

    public static Location updateLocation(Context context, Activity activity, LocationListener locationListener) {
        Location location = null;
        Criteria criteria = new Criteria();
        boolean hasPermission = true;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean hasAccessFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean hasAccessCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if ( !hasAccessFineLocationPermission && !hasAccessCoarseLocationPermission) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                activity.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);

            }
            hasPermission = false;
        }

        String provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {

            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
            if (location != null) {
                locationListener.onLocationChanged(location);
            } else {
                if (hasPermission) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                // Location by Rede
                if (location != null) {
                    locationListener.onLocationChanged(location);
                }
            }
        } else {
            if (hasPermission) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }

        return location;
    }

    public static void removeUpdates(Context context, Activity activity, LocationListener locationListener) {

        boolean hasPermission = true;
        boolean hasAccessFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean hasAccessCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if ( !hasAccessFineLocationPermission && !hasAccessCoarseLocationPermission) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                activity.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);

            }
            hasPermission = false;
        }

        if (hasPermission) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(locationListener);
        }
    }

    public static Location getLastKnownLocation(Context context, Activity activity) {

        boolean hasAccessFineLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean hasAccessCoarseLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if ( !hasAccessFineLocationPermission && !hasAccessCoarseLocationPermission) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                activity.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_FINE_LOCATION);

            }
            return null;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean hasLocationProvider(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean hasConnectivity(Context context){
        return ConnectivityUtils.getInstance(context).hasConnectivity();
    }

    public static LoginType getLoginType(){
        return loginType;
    }

    public static boolean isLoggedin(){
        return isLoggedIn;
    }

    public static GoogleApiClient getGoogleApiClient(Context context,
                                                     FragmentActivity fragmentActivity,
                                                     GoogleApiClient.OnConnectionFailedListener unresolvedConnectionFailedListener){

            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragmentActivity, unresolvedConnectionFailedListener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            return mGoogleApiClient;

    }

    public static void doGoogleLogin(Context context, String email, String name){
        DeviceInfo.loginType = LoginType.GOOGLE;
        DeviceInfo.isLoggedIn = true;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, email);
        settings.setPreferenceValue(Settings.USER_NAME, name);
    }

    private static void doGoogleLogout(Context context){
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        doEmailPwdLogout(context);

        try {
            if(mGoogleApiClient.isConnected()){
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
            }
            mGoogleApiClient = null;
        } catch(IllegalStateException e){
            Log.e(TAG, e.getMessage(), e);
        }

    }

    public static void doFacebookLogin(Context context, String email, String name){
        DeviceInfo.loginType = LoginType.FACEBOOK;
        DeviceInfo.isLoggedIn = true;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, email);
        settings.setPreferenceValue(Settings.USER_NAME, name);
    }

    private static void doFacebookLogout(Context context) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        doEmailPwdLogout(context);

        if (AccessToken.getCurrentAccessToken() == null) {
            return;
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null,
                HttpMethod.DELETE,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                    }
                }).executeAsync();
    }

    public static void doEmailPwdLogin(Context context, String email, LoginType loginType, String name){
        DeviceInfo.loginType = loginType;
        DeviceInfo.isLoggedIn = true;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, email);
        settings.setPreferenceValue(Settings.USER_NAME, name);
    }

    private static void doEmailPwdLogout(Context context){
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        DeviceInfo.loginType = null;
        DeviceInfo.isLoggedIn = false;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, null);
        settings.setPreferenceValue(Settings.USER_NAME, null);
    }

    public static void logout(Context context){
        LoginType loginType = getLoginType();
        if(loginType == null){
            return;
        }

        switch (loginType){
            case GOOGLE:
                doGoogleLogout(context);
                break;
            case FACEBOOK:
                doFacebookLogout(context);
                break;
            case EMAIL_PWD:
                doEmailPwdLogout(context);
                break;
            default:
                doEmailPwdLogout(context);
                break;
        }
    }

}