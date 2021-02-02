package com.attendo.fcm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.attendo.util.NotificationUtil;
import com.attendo.util.SettingUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
        {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                String token = task.getResult().getToken();
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        SettingUtil settingUtil = new SettingUtil();

        if (!(settingUtil.isTimeAutomatic(getApplicationContext()))) {
            Log.d(TAG, "Automatic date and time is not enabled");
            return;
        }

        String isScheduled = data.get("isScheduled");
        if (isScheduled.equals("true")) {
            String scheduledTime = data.get("scheduledTime");
        } else {
            showNotification(title);
        }

    }

    public void showNotification(String title) {
        NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(title);
    }

    public int generateRequestCode(String dateTimeString) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault());
        Date scheduledTime = sd.parse(dateTimeString);

        String fullTime = scheduledTime.toString();
        String hourAndMin = fullTime.substring(11, 13) + fullTime.substring(14, 16);
        int hourMin = Integer.parseInt(hourAndMin);

        return hourMin;
    }
}


