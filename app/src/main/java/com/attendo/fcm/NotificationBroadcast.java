package com.attendo.fcm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.impl.utils.ForceStopRunnable;

import com.attendo.fcm.NotifyWorker;
import com.attendo.fcm.ScheduledWorker;
import com.attendo.ui.main.BottomNavMainActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class NotificationBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      //  String body=intent.getStringExtra("body");

               
        if(!isWorkScheduled("Notify", context) ){
            Data notificationData= new Data.Builder()
                    .putString("body","test")
                    .build();
                  System.out.println("Not Scehduled");
            PeriodicWorkRequest work= new PeriodicWorkRequest.Builder(NotifyWorker.class,24, TimeUnit.HOURS)
                    .setInputData(notificationData).build();


            WorkManager.getInstance(context).enqueueUniquePeriodicWork("Notify",ExistingPeriodicWorkPolicy.REPLACE,work);

        }


    }
    private boolean isWorkScheduled(String tag, Context context) {
        WorkManager instance = WorkManager.getInstance(context);
        ListenableFuture<List<WorkInfo>> statuses = instance.getWorkInfosForUniqueWork(tag);
        try {
            boolean running = false;
            List<WorkInfo> workInfoList = statuses.get();
            for (WorkInfo workInfo : workInfoList) {
                WorkInfo.State state = workInfo.getState();
                running = state == WorkInfo.State.RUNNING | state == WorkInfo.State.ENQUEUED;
            }
            return running;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void setnotification(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,22);
      //  WorkManager.getInstance(context).cancelAllWork();
        calendar.set(Calendar.MINUTE,30);
        Intent intent = new Intent(context, NotificationBroadcast.class);
        PendingIntent pendingIntent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent
            );
        }
        else
        {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent
            );

        }        }
}
