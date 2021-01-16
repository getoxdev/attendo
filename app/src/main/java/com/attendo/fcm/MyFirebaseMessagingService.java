package com.attendo.fcm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.attendo.util.NotificationUtil;
import com.attendo.util.SettingUtil;
import com.firebase.client.annotations.NotNull;
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
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("message");
        SettingUtil settingUtil = new SettingUtil();

        if (!(settingUtil.isTimeAutomatic(getApplicationContext()))) {
            Log.d(TAG, "Automatic date and time is not enabled");
            return;
        }

        String isScheduled = data.get("isScheduled");
        if (isScheduled == "true") {
            String scheduledTime = data.get("scheduledTime");
            try {
                scheduleAlarm(scheduledTime, title, message);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            showNotification(title, message);
        }


    }

    public void scheduleAlarm(String scheduledTimeString, String title, String message) throws ParseException
    {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
        alarmIntent.putExtra("notification_title", title);
        alarmIntent.putExtra("notification_message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduledTime = sd.parse(scheduledTimeString);

        alarmManager.set(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), pendingIntent);

    }

    public void showNotification(String title, String message) {
        NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(title, message);

    }

    @Override
    public void onNewToken(@NonNull String s) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.e("TAG", "Get InstanceId failed", task.getException());
                    return;
                }

                String token = task.getResult().getToken();
                Log.e("My Token", token);
            }
        });
    }
}


