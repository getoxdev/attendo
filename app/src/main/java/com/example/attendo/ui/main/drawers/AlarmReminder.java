package com.example.attendo.ui.main.drawers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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
        String message = intent.getStringExtra("label");


        //When notification is tapped, Home screen come is logged in
        Intent mainIntent = new Intent(context, AuthenticationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0);

        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){

            Notification.Builder builder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.foreground_app_icon)
                    .setContentTitle("Class Reminder!")
                    .setContentText(message)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVibrate(new long[] {1000,500,1000,500,1000,500})
                    .setAutoCancel(true)
                    ;

            //notify
            NM.notify(notificationId,builder.build());

        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

            //Notification channel
            CharSequence channelName = "My notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {1000,500,1000,500,1000,500});
            channel.setBypassDnd(true);
            NM.createNotificationChannel(channel);
        }

        //prepeare Notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.foreground_app_icon)
                .setContentTitle("Class Reminder!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVibrate(new long[] {1000,500,1000,500,1000,500})
                .setAutoCancel(true)
                ;

        //notify
        NM.notify(notificationId,builder.build());

    }


}