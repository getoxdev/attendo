package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.Id;
import com.attendo.data.model.Reminder;
import com.attendo.data.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class ReminderViewModel extends AndroidViewModel
{
    private ApiHelper apiHelper;
    private MutableLiveData<Response> reminderResponse;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);
        apiHelper = new ApiHelper(application);
        reminderResponse=new MutableLiveData<>();
    }
    public MutableLiveData<Response> getReminderResponse(){
        return reminderResponse;
    }

    public void setReminder(Reminder reminder)
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
                    Log.i("response",Integer.toString(response.code()));
                    Log.i("ID",response1.getReminder().get_id());
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
    }
    public void cancelReminder(){
        apiHelper.cancelReminder().enqueue(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, retrofit2.Response<Id> response) {
                Log.i("responseOfCancel",Integer.toString(response.code()));

            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {

            }
        });
    }


}
