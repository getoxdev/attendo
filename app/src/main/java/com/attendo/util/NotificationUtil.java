package com.attendo.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.attendo.R;
import com.attendo.ui.main.drawers.reminder.FragmentExamReminder;
import com.firebase.client.annotations.NotNull;

import java.util.Random;

public  class NotificationUtil {
     Context context;
    public NotificationUtil(@NotNull Context context) {
        super();
        this.context = context;
    }

    public  void showNotification(@NotNull String title, @NotNull String message) {
        Intent intent = new Intent(context, FragmentExamReminder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "default";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.Builder notificationBuilder = (new NotificationCompat.Builder(this.context, channelId))
                .setColor(ContextCompat.getColor(this.context, R.color.blue))
                .setSmallIcon(R.drawable.app_icon_middle_portion_removed)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(channelId, (CharSequence)"Default Channel", 4);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(110, notificationBuilder.build());
        }



}