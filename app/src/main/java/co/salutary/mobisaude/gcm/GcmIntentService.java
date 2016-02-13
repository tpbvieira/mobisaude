/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.salutary.mobisaude.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.activities.SplashActivity;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getSimpleName();

	private static long[] pattern = new long[]{500, 500, 1000, 1000};

	public GcmIntentService() {

        super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		// The getMessageType() intent parameter must be the intent you received in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that GCM will be
			 * extended in the future with new message types, just ignore any message types you're
			 * not interested in, or that you don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				Log.i(TAG, "Send error: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				Log.i(TAG, "Send error: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// Post notification of received message.
				Log.i(TAG, "Received: " + extras.toString());
				onMessage(extras.getString("payload"));
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}		

	public void onMessage(String message){
		try{
			JSONObject json = new JSONObject(message);
			JSONObject android = new JSONObject(json.getString("android"));
			String title = android.optString("title", getString(R.string.app_name));
			String mess = android.optString("alert", "");
			String vibrate = android.optString("vibrate", "false");
			generateNotification(getApplicationContext(), title, mess, vibrate);
		}
		catch(Exception e){
			Log.d(TAG, e.getMessage());
		}
	}

	private void generateNotification(Context context, String title, String message, String vibrate) {

		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(context);
		ncb.setAutoCancel(true);
		ncb.setSmallIcon(R.drawable.ic_launcher);

		ncb.setContentTitle(title);
		ncb.setTicker(message);
		ncb.setContentText(message);
		
		if(vibrate.equals("true")){
			ncb.setVibrate(pattern);
		}

		PendingIntent pi = null;

		Intent i = new Intent(context, SplashActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		pi = PendingIntent.getActivity(context, 0, i, 0);

		if(pi != null) {
            ncb.setContentIntent(pi);
        }
		Notification notification = ncb.build();
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 500;
		notification.ledOffMS = 1000;
		notificationManager.notify((int)System.currentTimeMillis(), notification);

	}
}