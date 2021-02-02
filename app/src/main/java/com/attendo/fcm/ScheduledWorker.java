package com.attendo.fcm;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.attendo.util.NotificationUtil;
import com.firebase.client.annotations.NotNull;

public  class ScheduledWorker extends Worker
{
    public ScheduledWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title=getInputData().getString("title");

        NotificationUtil notificationUtil=new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(title);

        return Result.success();
    }
}
