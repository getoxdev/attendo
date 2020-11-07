package com.attendo.data.api;

import android.content.Context;

import com.attendo.data.model.Id;
import com.attendo.data.model.Response;
import com.attendo.data.model.Reminder;
import com.attendo.retrofit.RetrofitProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;

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
    public Call<ResponseBody> cancelReminder(String id) {
        return api.cancelReminder(id);
    }
}
