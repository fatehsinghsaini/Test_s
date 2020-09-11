package com.os.busservice.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.os.busservice.R;


public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils(Context base, Uri soundUri) {
        super(base);
        createChannels(soundUri);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(Uri soundUri) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(getString(R.string.default_notification_channel_id),
                getString(R.string.default_notification_channel_name), NotificationManager.IMPORTANCE_DEFAULT);

        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);

        androidChannel.setSound(soundUri, attributes);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationCompat.Builder getAndroidChannelNotification(Context context, String title, String body, PendingIntent intent) {

        Bitmap largeIcon = BitmapFactory.decodeResource((context).getResources(), R.mipmap.ic_launcher);
            return new NotificationCompat.Builder(getApplicationContext(), getString(R.string.default_notification_channel_id))
                    .setContentTitle(title)
                    .setContentIntent(intent)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
//                      .setSound(sound)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker(context.getString(R.string.app_name))
                    .setLargeIcon(largeIcon);

    }

}
