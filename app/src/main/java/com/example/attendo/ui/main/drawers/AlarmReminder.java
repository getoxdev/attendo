package com.example.attendo.ui.main.drawers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.attendo.ui.auth.AuthenticationActivity;

public class AlarmReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //get id and message from intent
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");

        //When notification is tapped, Home screen come is logged in
        Intent mainIntent = new Intent(context, AuthenticationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0);

        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //prepeare Notification
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Reminder!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);


        //notify
        NM.notify(notificationId,builder.build());

    }
}