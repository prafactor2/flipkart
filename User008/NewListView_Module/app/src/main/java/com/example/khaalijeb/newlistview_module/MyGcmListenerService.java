package com.example.khaalijeb.newlistview_module;

/**
 * Created by Davinci on 9/9/2015.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Date;

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d("gcm", "aaya :");
        String to = data.getString("to");
        Log.d("notifications", "to: " + to);
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (to.equals("push")) {
            Log.d("gcm", "to/push :" + message);
            pushNotification(message);
        } else if (to.equals("follow")) {
            Log.d("gcm", "to/follow :" + message);
            String total = data.getString("total");
            settings.edit().putInt("follow_notifications", Integer.parseInt(total)).apply();
            followNotification(message);
        } else if (to.equals("notifications")) {
            Log.d("gcm", "to/notifications :" + message);
            String total = data.getString("total");
            settings.edit().putInt("notifications_notifications", Integer.parseInt(total)).apply();
            notificationsNotification(message);
        }
    }

    private void pushNotification(String message) {

        Intent intent = new Intent(this, ShopSearch.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setSmallIcon(R.drawable.cast_ic_notification_0)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Date date = new Date();
        int NOTIFICATION_ID = (int) date.getTime();

        notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
    }

    public void followNotification(String message) {
        Intent intent = new Intent("follow_notifications");
        Log.d("gcm", "follow_brand message: " + message);
        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        this.sendBroadcast(intent);
    }

    public void notificationsNotification(String message) {
        Intent intent = new Intent("notifications_notifications");
        Log.d("gcm", "notifications message: " + message);
        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        this.sendBroadcast(intent);
    }
}
