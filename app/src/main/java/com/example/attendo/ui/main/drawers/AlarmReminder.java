package com.example.attendo.ui.main.drawers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.attendo.R;
import com.example.attendo.ui.auth.AuthenticationActivity;

public class AlarmReminder extends BroadcastReceiver {

    int startfrom=0;
    int endAt=20000;
    Runnable stopPlayerTask=new Runnable() {
        @Override
        public void run() {
            mediaPlayer.pause();
        }
    };

    private static final String CHANNEL_ID="SAMPLE_CHANNEL";
    public MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {

        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.seekTo(startfrom);
        mediaPlayer.start();

        Handler handler=new Handler();
        handler.postDelayed(stopPlayerTask,endAt);

        //get id and message from intent
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");

        //When notification is tapped, Home screen come is logged in
        Intent mainIntent = new Intent(context, AuthenticationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0);

        NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

            //Notification channel
            CharSequence channelName = "My notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            NM.createNotificationChannel(channel);
        }

        //prepeare Notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.foreground_app_icon)
                .setContentTitle("Class Reminder!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound( Settings.System.DEFAULT_RINGTONE_URI)
                .setOnlyAlertOnce(true);

        //notify
        NM.notify(notificationId,builder.build());

    }
}