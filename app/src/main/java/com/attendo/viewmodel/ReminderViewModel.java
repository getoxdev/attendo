package com.attendo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.Reminder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderViewModel extends AndroidViewModel
{
    private ApiHelper apiHelper;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);

        apiHelper = new ApiHelper(application);
    }

    public void setReminder(Reminder reminder)
    {
        apiHelper.sendReminder(reminder).enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call, Response<Reminder> response) {

            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {

            }
        });
    }
}
