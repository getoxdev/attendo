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

public  class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title=intent.getStringExtra("title");

        Data notificationData= new Data.Builder()
                .putString("title",title)
                .build();

        OneTimeWorkRequest work= new OneTimeWorkRequest.Builder(ScheduledWorker.class)
                .setInputData(notificationData)
                .build();

        //start worker
        WorkManager.getInstance(context).beginWith(work).enqueue();
    }
}
