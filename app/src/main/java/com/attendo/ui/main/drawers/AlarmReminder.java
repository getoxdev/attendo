package com.attendo.ui.main.drawers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.attendo.R;
import com.attendo.ui.auth.AuthenticationActivity;

public class AlarmReminder extends BroadcastReceiver {

    private static final String CHANNEL_ID="CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent)
    {

        //get id and message from intent
        String message = intent.getExtras().getString("Label");

        //When notification is tapped, Home screen come is logged in
        Intent mainIntent = new Intent(context, AuthenticationActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context,1,mainIntent,0);

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
            channel.setVibrationPattern(new long[]{300, 400, 300});
            channel.setBypassDnd(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.canShowBadge();
            NM.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle("Class Reminder")
                    .setContentText(message)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.transparent_app))
                    .setSmallIcon(R.drawable.transparent_app)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLights(Color.BLUE, 1000, 300)
                    .setAutoCancel(true);

            //notify
            NM.notify(1, builder.build());
        }
        else
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle("Class Reminder")
                    .setContentText(message)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.transparent_app))
                    .setSmallIcon(R.drawable.transparent_app)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLights(Color.BLUE, 1000, 300)
                    .setVibrate(new long[]{300, 400, 300})
                    .setAutoCancel(true);

            //notify
            NM.notify(1, builder.build());
        }
    }
}