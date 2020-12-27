package com.attendo.data.api;

import com.attendo.data.model.CreateSchedule;
import com.attendo.data.model.Id;
import com.attendo.data.model.Response;
import com.attendo.data.model.Reminder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface  ApiService
{
    @POST("api/reminder")
    Call<Response> sendReminder(@Body Reminder reminder);

    @POST("api/class")
    Call<Response> sendSchedule(@Body CreateSchedule createSchedule);

    @POST("api/reminder/{reminderId}/")
    Call<ResponseBody> cancelReminder (@Path("reminderId") String id);

    


}