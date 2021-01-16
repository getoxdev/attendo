package com.attendo.data.api;

import android.content.Context;

import com.attendo.data.model.CreateClass;
import com.attendo.data.model.GetSchedule;
import com.attendo.data.model.JoinClass;
import com.attendo.data.model.Response;
import com.attendo.data.model.Reminder;
import com.attendo.data.model.ResponseCreateClass;
import com.attendo.data.model.ResponseGetSchedule;
import com.attendo.data.model.ResponseJoinClass;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;
import com.attendo.data.model.ScheduleEdit;
import com.attendo.retrofit.RetrofitProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @Override
    public Call<ResponseCreateClass> createclass(@Body CreateClass createClass){
        return api.createclass(createClass);
    }

    @Override
    public Call<ResponseJoinClass> joinclass(@Body JoinClass joinClass){
        return api.joinclass(joinClass);
    }

    @GET("api/schedule")
    public Call<ResponseSchedule> createschedule(@Body Schedule schedule){
        return api.createschedule(schedule);
    }

    @POST("api/schedule/edit")
    public Call<ResponseSchedule> updateschedule(@Body ScheduleEdit scheduleEdit){
        return api.updateschedule(scheduleEdit);
    }

    @Override
    public Call<ResponseGetSchedule> getschedule(String classId, String day){
        return getschedule(classId, day);
    }

}
