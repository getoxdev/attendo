package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.Reminder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderViewModel extends AndroidViewModel
{
    private ApiHelper apiHelper;
    private MutableLiveData<Reminder> reminderResponse;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);

        apiHelper = new ApiHelper(application);
        reminderResponse=new MutableLiveData<>();
    }
    public MutableLiveData<Reminder> getReminderResponse(){
        return reminderResponse;
    }

    public void setReminder(Reminder reminder)
    {
        apiHelper.sendReminder(reminder).enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call, Response<Reminder> response) {
                if(response.code()<300)
                {
                    reminderResponse.postValue(response.body());
                    Log.i("response",Integer.toString(response.code()));
                }
                else if(response.code()>=400)
                {
                    reminderResponse.postValue(null);
                    Log.i("responseNext",Integer.toString(response.code()));
                }
            }
            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {
                reminderResponse.postValue(null);
                Log.i("responseFailed","Failed Response");

            }
        });
    }
    public void cancelReminder(Reminder reminder){
        apiHelper.sendReminder(reminder).enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call, Response<Reminder> response) {
                Log.i("cancelReminder",Integer.toString(response.code()));
            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t){
                Log.i("cancelReminder","Failed Response");

            }
        });
    }
}
