package com.nts.youtubemusic.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.ui.main.MainActivity;

import timber.log.Timber;

public class AlarmMorning extends BroadcastReceiver {
    private NotificationManager notificationManager;
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MainActivity.class);
        NotificationChannel notificationChannel = new NotificationChannel(Constant.Alarm_ID, Constant.PRIORITY_Alarm_ID, NotificationManager.IMPORTANCE_LOW);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, 0);
        Notification notification = new Notification.Builder(context, Constant.Alarm_ID)
                .setContentText(context.getString(R.string.text_notifi))
                .setContentTitle(context.getString(R.string.title_notifi))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_title_app)
                .build();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(2, notification);
    }

}


