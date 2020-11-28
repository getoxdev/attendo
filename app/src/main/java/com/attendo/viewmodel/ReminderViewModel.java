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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ReminderViewModel extends AndroidViewModel
{
    private ApiHelper apiHelper;
    private MutableLiveData<Response> reminderResponse;
    private MutableLiveData<ResponseBody> idresponse;

    public ReminderViewModel(@NonNull Application application)
    {
        super(application);
        apiHelper = new ApiHelper(application);
        reminderResponse=new MutableLiveData<>();
        idresponse=new MutableLiveData<>();
    }
    public MutableLiveData<Response> getReminderResponse(){
        return reminderResponse;
    }

    public MutableLiveData<ResponseBody> getIdresponse() {
        return idresponse;
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

    public void setcancelReminder(String id)
    {
       apiHelper.cancelReminder(id).enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response)
           {
               if (response.code() < 300)
               {
                   Log.i("responseofcancel", Integer.toString(response.code()));
                   getIdresponse().postValue(response.body());
               }
               else
               if (response.code() >= 400) {
                   getIdresponse().postValue(null);
               }
           }
           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               getIdresponse().postValue(null);
           }
       });
    }


}