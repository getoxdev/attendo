package com.attendo.api;

import com.attendo.model.Reminder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/reminder")
    Call<Reminder> setReminder(@Body Reminder reminder);
}
