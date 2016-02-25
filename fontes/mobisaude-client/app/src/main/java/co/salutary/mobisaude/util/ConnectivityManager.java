package co.salutary.mobisaude.util;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityManager {

    private static final String TAG = ConnectivityManager.class.getSimpleName();

    private static ConnectivityManager instance = null;
    private android.net.ConnectivityManager connectivityManager;

    public static ConnectivityManager getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectivityManager(context);
        }
        return instance;
    }

    public ConnectivityManager(Context ctx) {
        connectivityManager = (android.net.ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public int requisitConexaoMobile() {
        try {
            return connectivityManager.startUsingNetworkFeature(android.net.ConnectivityManager.TYPE_MOBILE, "enableHIPRI");
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return 0;
        }
    }

    public boolean isConnected() {
        try {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
}