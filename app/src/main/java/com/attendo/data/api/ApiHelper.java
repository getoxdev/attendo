package com.attendo.data.api;

import android.content.Context;

import com.attendo.data.model.CreateClass;
import com.attendo.data.model.CreateSchedule;
import com.attendo.data.model.Id;
import com.attendo.data.model.JoinClass;
import com.attendo.data.model.Response;
import com.attendo.data.model.Reminder;
import com.attendo.retrofit.RetrofitProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

public class ApiHelper implements ApiService
{
    private static ApiHelper instance;
    private ApiService api;
    public ApiHelper(Context context) {
        api = RetrofitProvider.getInstance(context).create(ApiService.class);
    }

    public static ApiHelper getInstance(Context context){
        if(instance == null){
            synchronized (ApiHelper.class){
                if(instance == null){
                    instance = new ApiHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Call<Response> sendReminder(Reminder reminder) {
        return api.sendReminder(reminder);
    }

    @Override
    public Call<Response> sendSchedule(CreateSchedule createSchedule) {
        return null;
    }

    @Override
    public Call<ResponseBody> cancelReminder(String id) {
        return api.cancelReminder(id);
    }

    @Override
    public Call<CreateClass> createclass(@Body CreateClass createClass){
        return api.createclass(createClass);
    }

    @Override
    public Call<JoinClass> joinclass(@Body JoinClass joinClass){
        return api.joinclass(joinClass);
    }
}
