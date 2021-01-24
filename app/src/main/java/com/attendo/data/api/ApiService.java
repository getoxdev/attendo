package com.attendo.data.api;

import com.attendo.data.model.schedule.CreateClass;
import com.attendo.data.model.schedule.GetStudentListResponse;
import com.attendo.data.model.schedule.JoinClass;
import com.attendo.data.model.reminder.Response;
import com.attendo.data.model.reminder.Reminder;
import com.attendo.data.model.schedule.Notice;
import com.attendo.data.model.schedule.ResDeleteNotice;
import com.attendo.data.model.schedule.ResponseCreateClass;
import com.attendo.data.model.schedule.ResponseDeleteSchedule;
import com.attendo.data.model.schedule.ResponseGetNotice;
import com.attendo.data.model.schedule.ResponseGetSchedule;
import com.attendo.data.model.schedule.ResponseJoinClass;
import com.attendo.data.model.schedule.ResponseNotice;
import com.attendo.data.model.schedule.ResponseSchedule;
import com.attendo.data.model.schedule.Schedule;
import com.attendo.data.model.schedule.ScheduleDelete;
import com.attendo.data.model.schedule.ScheduleEdit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("api/schedule/delete")
    Call<ResponseDeleteSchedule> DeleteSchedule(@Body ScheduleDelete scheduleDelete);

    @GET("api/schedule/{classId}/{day}")
    Call<ResponseGetSchedule> getschedule(@Path("classId") String class_Id, @Path("day") String day);

    @GET("api/user/students/{classId}")
    Call<GetStudentListResponse> getStudentList(@Path("classId") String classId);

    @POST("api/notice/{classId}")
    Call<ResponseNotice> createNotice(@Path("classId") String class_Id,@Body Notice notice);

    @POST("api/notice/edit/{noticeId}")
    Call<ResponseNotice>  updateNotice(@Path("noticeId")String noticeId,@Body Notice notice);

    @GET("api/notice/{noticeId}")
    Call<ResponseNotice> getNotice(@Path("noticeId") String noticeId);

    @GET("api/notice/all/{classId}")
    Call<ResponseGetNotice> getAllNotice(@Path("classId") String classId);

    @DELETE("api/notice/{noticeId}/{classId}")
    Call<ResDeleteNotice> deleteNotice(@Path("noticeId")String noticeId,@Path("classId") String classId);



}