package com.attendo.fcm;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.attendo.util.NotificationUtil;
import com.firebase.client.annotations.NotNull;

public  class ScheduledWorker extends Worker {


    private static final String TAG = "ScheduledWorker";

    public ScheduledWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG,"Work start");

        String title=getInputData().getString("notification_title");
        String message=getInputData().getString("notification_message");

        NotificationUtil notificationUtil=new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(title,message);

        Log.d(TAG,"Work done");
        return Result.success();
    }
}
