package co.salutary.mobisaude.util;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityUtils {

    private static final String TAG = ConnectivityUtils.class.getSimpleName();

    private static ConnectivityUtils instance = null;
    private android.net.ConnectivityManager connectivityManager;

    public static ConnectivityUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectivityUtils(context);
        }
        return instance;
    }

    private ConnectivityUtils(Context ctx) {
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

    public boolean hasConnectivity() {
        try {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
}