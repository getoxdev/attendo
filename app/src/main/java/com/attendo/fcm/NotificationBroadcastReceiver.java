package com.attendo.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

public  class NotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title=intent.getStringExtra("notification_title");
        String messgae=intent.getStringExtra("notification_message");


        Data notificationData= new Data.Builder()
                .putString("notification_title",title)
                .putString("notification_messgae",messgae)
                .build();

        OneTimeWorkRequest work= new OneTimeWorkRequest.Builder(ScheduledWorker.class)
                .setInputData(notificationData)
                .build();

        //start worker
        WorkManager.getInstance(context).beginWith(work).enqueue();

        Log.d("start worker", "WorkManager is Enqueued.");


    }
}
