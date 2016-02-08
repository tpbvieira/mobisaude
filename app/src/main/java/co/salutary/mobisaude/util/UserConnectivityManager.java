package co.salutary.mobisaude.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class UserConnectivityManager {

    private static final String TAG = UserConnectivityManager.class.getSimpleName();

    private static UserConnectivityManager instance = null;
    private ConnectivityManager connectivityManager;

    public static UserConnectivityManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserConnectivityManager(context);
        }
        return instance;
    }

    public UserConnectivityManager(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public int requisitConexaoMobile() {
        try {
            return connectivityManager.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "enableHIPRI");
        } catch (Exception e) {
            Log.d(TAG, "Error!", e);
            return 0;
        }
    }

    public boolean isConnected() {
        try {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
        } catch (Exception e) {
            Log.d(TAG, "Error!", e);
            return false;
        }
    }
}