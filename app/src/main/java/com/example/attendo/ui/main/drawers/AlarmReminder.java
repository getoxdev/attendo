package com.example.attendo.ui.main.drawers;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.example.attendo.R;
import com.example.attendo.ui.auth.AuthenticationActivity;

public class AlarmReminder extends BroadcastReceiver {

    private static final String CHANNEL_ID="SAMPLE_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {

        //get id and message from intent
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");


        //When notification is tapped, Home screen come is logged in
        Intent mainIntent = new Intent(context, AuthenticationActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context,101,mainIntent,0);

        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            //Notification channel
            CharSequence channelName = "Attendo Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription("Reminder");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            NM.createNotificationChannel(channel);
        }

        //prepeare Notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.foreground_app_icon)
                .setContentTitle("Class Reminder")
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setLights(Color.BLUE, 1000, 300)
                .setVibrate(new long[]{300, 400, 300})
                .setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        //notify
        NM.notify(notificationId,builder.build());

    }


}