package com.attendo.viewmodel;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
    private ApiHelper apiHelper;
    private MutableLiveData<Response> reminderResponse;
    private RemRepository remRepository;
    private LiveData<List<RemEntity>> allReminders;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);
        apiHelper = new ApiHelper(application);
        reminderResponse=new MutableLiveData<>();

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
    public MutableLiveData<Response> getReminderResponse(){
        return reminderResponse;
    }

    /*public void setReminder(Reminder reminder)
    {
        apiHelper.sendReminder(reminder).enqueue(new Callback<Response>()
        {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response)
            {
                if(response.code()<300)
                {
                    Response response1=response.body();
                    reminderResponse.postValue(response1);
                }
                else if(response.code()>=400)
                {
                    reminderResponse.postValue(null);
                    Log.i("responseNext", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t)
            {
                reminderResponse.postValue(null);
            }
        });
    }*/

    public void setReminder(int requestCode,String scheduledTimeString, String title)
    {
        Log.e("RequestCodeSet",String.valueOf(requestCode));
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getApplicationContext(), ReminderBroadcastReceiver.class);
        alarmIntent.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        Date scheduledTime = null;
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
    }

}
