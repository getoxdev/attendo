package com.attendo.data.api;

import com.attendo.data.model.Reminder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService
{
    @POST("api/reminder")
    Call<Reminder> sendReminder(@Body Reminder reminder);
}
