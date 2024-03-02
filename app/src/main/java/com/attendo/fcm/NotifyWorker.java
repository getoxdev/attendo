package com.attendo.fcm;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.attendo.R;
import com.attendo.ui.main.BottomNavMainActivity;

public  class NotifyWorker extends Worker
{
    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String body=getInputData().getString("body");

        Intent intent = new Intent(getApplicationContext(), BottomNavMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(getApplicationContext(), 102, intent,  PendingIntent.FLAG_IMMUTABLE);
        }else{
           pi= PendingIntent.getBroadcast(getApplicationContext(), 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        //PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 102, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("223", "AttendO", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("notification");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "223")
                .setContentTitle("Mark your Attendance")
                .setContentText("Did you mark your attendance today? Touch to update!")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pi)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.app_icon_middle_portion_removed);

        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(102, notificationBuilder.build());

        return Result.success();
    }
}
