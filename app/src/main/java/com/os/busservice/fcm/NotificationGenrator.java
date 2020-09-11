package com.os.busservice.fcm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.core.app.NotificationCompat;

import com.os.busservice.R;

import java.util.Random;

public class NotificationGenrator {


    private NotificationUtils mNotificationUtils;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    public void generateNotification(Context context, String title_top, String body, String userId, Intent notificationIntent, String soundValue) {
        int m = 0;
            Random random = new Random();
            m = random.nextInt(9999 - 1000) + 1000;



        PendingIntent intent = PendingIntent.getActivity(context, m, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource((context).getResources(), R.drawable.notification_icon);
        Log.d("generateNotification", "11111");

   /*     Uri sound =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName()
                + "/" + R.raw.alert);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("generateNotification", "2222");
            mNotificationUtils = new NotificationUtils(context, null);
            NotificationCompat.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification(context, title_top, body, intent);
             nb.setSmallIcon(R.drawable.notification_icon);
            mNotificationUtils.getManager().notify(m, nb.build());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d("generateNotification", "3333");
            Resources res = context.getResources();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(title_top)
                    .setContentText(body).setContentIntent(intent)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setTicker(context.getString(R.string.app_name))
                    .setLargeIcon(largeIcon);

            Notification n = mBuilder.build();
            n.flags |= Notification.FLAG_AUTO_CANCEL;
            n.defaults |= Notification.DEFAULT_VIBRATE;
            NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notifyMgr.notify(m, mBuilder.build());
            // notificationManager.notify(m,n);

        } else {
            Log.d("generateNotification", "4444");
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notif = new Notification.Builder(context).setContentTitle(title_top).setContentText(body).setContentIntent(intent).setSmallIcon(R.drawable.notification_icon).setBadgeIconType(R.drawable.notification_icon).setLargeIcon(largeIcon).setStyle(new Notification.BigTextStyle().bigText(body))
                    ///  .setStyle(new Notification.BigPictureStyle().bigPicture(largeIcon)) /
                    .build();
            int smallIconId = context.getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
            Log.d("smallIconId", "" + smallIconId);
            if (smallIconId != 0) {
                notif.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
                notif.bigContentView.setViewVisibility(smallIconId, View.INVISIBLE);
            }
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            // Play default notification sound
            notif.defaults |= Notification.DEFAULT_SOUND;

            // Vibrate if vibrate is enabled
            notif.defaults |= Notification.DEFAULT_VIBRATE;
            notificationManager.notify(m, notif);
        }

    }
}
