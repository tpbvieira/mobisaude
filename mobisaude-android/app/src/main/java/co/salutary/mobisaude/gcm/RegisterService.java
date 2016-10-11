package co.salutary.mobisaude.gcm;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.salutary.mobisaude.config.DeviceInfo;

public class RegisterService extends Service{

    private static final String TAG = RegisterService.class.getSimpleName();

    private final IBinder mBinder = new MyBinder();

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	static String SENDER_ID = "459116382635";
	static GoogleCloudMessaging gcm;
	static Context context;
	static String regid = "";

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		context = RegisterService.this;

		Bundle extras = intent.getExtras();
		if(extras != null)
			regid = extras.getString("regid", "");
		Log.d(TAG, "registration id " + regid);
		if(regid.equals("")){
			registerInBackground();
		}
		else{
			Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					sendRegistrationIdToBackend();
				}
			});
		}

		return Service.START_NOT_STICKY;
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					Log.d(TAG, msg);

					// You should send the registration ID to your server over HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
					mainHandler.post(new Runnable() {
						@Override
						public void run() {
							sendRegistrationIdToBackend();
						}
					});

					// For this demo: we don't need to send it because the device will send
					// upstream messages to a server that echo back the message using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.d(TAG, msg);
				//mDisplay.append(msg + "\n");
			}
		}.execute(null, null, null);
	}  	

	private void sendRegistrationIdToBackend() {
		// Your implementation here.
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", "android");
		data.put("channel", "friend_request");
		//data.put("device_token", "4e7a297ccc173bec93a2b81");
		data.put("device_token", regid);
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = DeviceInfo.getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DeviceInfo.PROPERTY_REG_ID, regId);
		editor.putInt(DeviceInfo.PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}	

	private void storeRegistrationGCMCLientStatus(Context context, String status) {
		final SharedPreferences prefs = DeviceInfo.getGcmPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DeviceInfo.REGISTRATION_GCM_CLIENT, status);
		editor.commit();
	}	

	private int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {			
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
        return mBinder;
	}

	public class MyBinder extends Binder {
		RegisterService getService() {
			return RegisterService.this;
		}
	}

}