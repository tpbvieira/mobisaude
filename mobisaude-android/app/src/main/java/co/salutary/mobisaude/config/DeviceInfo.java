package co.salutary.mobisaude.config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.activities.LoginActivity;
import co.salutary.mobisaude.gcm.RegisterService;
import co.salutary.mobisaude.util.ConnectivityUtils;

public class DeviceInfo {

    private static final String TAG = new Object(){}.getClass().getName();

    private static boolean isLoggedIn = false;
    private static LoginType loginType = null;
    public static enum LoginType {
        GOOGLE, FACEBOOK, EMAIL_PWD
    }

    public static GoogleApiClient mGoogleApiClient;

    public static boolean hasToken = false;

    public static double lastLatitude;
    public static double lastLongitude;

    public static String statusMesage = "";

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String REGISTRATION_GCM_CLIENT = "registration_gcm_client";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";

    static GoogleCloudMessaging gcm;
    public static Context context;
    static String regid;

    public static void setUpGCM(Context ctx) {
        context = ctx;

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices(ctx)) {
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                Intent service = new Intent(context, RegisterService.class);
                context.startService(service);
            } else {
                String status = getRegistrationGCMCLientStatus(context);

                if (!status.equals("inserted")) {
                    Intent service = new Intent(context, RegisterService.class);
                    service.putExtra("regid", regid);
                    context.startService(service);
                }
            }

        } else {
            Log.i(TAG, ctx.getString(R.string.invalid_gps_apk));
        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    //ToDo
    @Deprecated
    public static boolean checkPlayServices(Context ctx) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) ctx, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, ctx.getString(R.string.not_supported));
            }
            return false;
        }
        return true;
    }


    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    public static SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
    }


    public static String getRegistrationGCMCLientStatus(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        return prefs.getString(REGISTRATION_GCM_CLIENT, "");
    }

    public static Location updateLocation(Context context, LocationListener locationListener) {
        Location location = null;
        Criteria criteria = new Criteria();
        boolean hasPermission = true;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions here to request the missing permissions,
            // and then overriding public void onRequestPermissionsResult(int requestCode,
            // String[] permissions, int[] grantResults) to handle the case where the user
            // grants the permission. See the documentation for ActivityCompat#requestPermissions
            // for more details.
            hasPermission = false;
        }

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

    public static void removeUpdates(Context context, LocationListener locationListener) {

        boolean hasPermission = true;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions here to request the missing permissions,
            // and then overriding public void onRequestPermissionsResult(int requestCode,
            // String[] permissions, int[] grantResults) to handle the case where the user
            // grants the permission. See the documentation for ActivityCompat#requestPermissions
            // for more details.
            hasPermission = false;
        }

        if (hasPermission) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public static Location getLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean hasLocationProvider(){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean thereIsWifiConnection(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean thereIsConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null || !i.isConnected() || !i.isAvailable()) {
            return false;
        }
        return true;
    }

    public static boolean hasConnectivity(Context context){
        return ConnectivityUtils.getInstance(context).hasConnectivity();
    }

    public static void login(Context context, String email, LoginType loginType, String name){
        DeviceInfo.loginType = loginType;
        DeviceInfo.isLoggedIn = true;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, email);
        settings.setPreferenceValue(Settings.USER_NAME, name);
    }

    public static void logout(Context context){
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        DeviceInfo.loginType = null;
        DeviceInfo.isLoggedIn = false;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, null);
        settings.setPreferenceValue(Settings.USER_NAME, null);
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
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(fragmentActivity, unresolvedConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }

    public static void googleLogin(Context context, String email, String name){
        DeviceInfo.loginType = LoginType.GOOGLE;
        DeviceInfo.isLoggedIn = true;
        Settings settings = new Settings(context);
        settings.setPreferenceValue(Settings.USER_EMAIL, email);
        settings.setPreferenceValue(Settings.USER_NAME, name);
    }

    public static void googleLogout(Context context){
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        try {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                        }
                    });
        } catch(IllegalStateException e){
            Log.e(TAG, e.getMessage(), e);
        }

        logout(context);
    }
}