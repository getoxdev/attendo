package com.attendo.data.api;

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
import com.attendo.data.model.schedule_list;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface  ApiService
{
    @POST("api/reminder")
    Call<Response> sendReminder(@Body Reminder reminder);

    @POST("api/reminder/{reminderId}/")
    Call<ResponseBody> cancelReminder (@Path("reminderId") String id);

    @POST("api/class")
    Call<ResponseCreateClass> createclass(@Body CreateClass createClass);

    @POST("api/class/join")
    Call<ResponseJoinClass> joinclass(@Body JoinClass joinClass);

    @POST("api/schedule")
    Call<ResponseSchedule> createschedule(@Body Schedule schedule);

    @POST("api/schedule/edit")
    Call<ResponseSchedule> updateschedule(@Body ScheduleEdit scheduleEdit);

    @GET("api/schedule/{classId}/{day}")
    Call<ResponseGetSchedule> getschedule(@Path("classId") String class_Id, @Path("day") String day);

}