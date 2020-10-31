package com.attendo.api;

import android.content.Context;

import com.attendo.model.Reminder;
import com.attendo.retrofit.RetrofitProvider;

import retrofit2.Call;

public class ApiHelper implements ApiService {
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
    public Call<Reminder> setReminder(Reminder reminder) {
        return api.setReminder(reminder);
    }
}
