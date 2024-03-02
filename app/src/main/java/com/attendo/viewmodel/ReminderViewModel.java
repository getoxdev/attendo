package com.attendo.viewmodel;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.reminder.Reminder;
import com.attendo.data.model.reminder.Response;
import com.attendo.data.rem.RemEntity;
import com.attendo.fcm.ReminderBroadcastReceiver;
import com.attendo.ui.main.drawers.reminder.RemRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReminderViewModel extends AndroidViewModel
{
    private RemRepository remRepository;
    private LiveData<List<RemEntity>> allReminders;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);
        remRepository =new RemRepository(application);
        allReminders=remRepository.getAllReminders();
    }
    public void insert(RemEntity remEntity){
         remRepository.insert(remEntity);
    }

    public void update(RemEntity remEntity){
        remRepository.update(remEntity);
    }

    public void delete(RemEntity remEntity){
        remRepository.delete(remEntity);
    }

    public LiveData<List<RemEntity>> getAllReminders(){
        return allReminders;
    }

    public void setReminder(int requestCode,String scheduledTimeString, String body)
    {
        Log.e("RequestCodeSet",String.valueOf(requestCode));
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getApplicationContext(), ReminderBroadcastReceiver.class);
        alarmIntent.putExtra("body",body);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else{
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'Z'", Locale.getDefault());
        Date scheduledTime = new Date();
        try {
            scheduledTime = sd.parse(scheduledTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), pendingIntent);
    }

    public void cancelReminder(int requestCode,String title)
    {
        Log.e("RequestCodeCancel",String.valueOf(requestCode));
        Intent alarmIntent = new Intent(getApplicationContext(), ReminderBroadcastReceiver.class);
        alarmIntent.putExtra("title", title);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else{
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
    }

}
