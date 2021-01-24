package com.attendo.data.api;

import android.content.Context;

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

    @Override
    public Call<ResponseGetSchedule> getschedule(String classId, String day){
        return api.getschedule(classId, day);
    }



    @Override
    public Call<ResponseSchedule> createschedule(@Body Schedule schedule){
        return api.createschedule(schedule);
    }

    @Override
    public Call<ResponseSchedule> updateschedule(@Body ScheduleEdit scheduleEdit){
        return api.updateschedule(scheduleEdit);
    }

    @Override
    public Call<ResponseDeleteSchedule> DeleteSchedule(ScheduleDelete scheduleDelete) {
        return api.DeleteSchedule(scheduleDelete);
    }


    @Override
    public Call<GetStudentListResponse> getStudentList(String classId) {
        return api.getStudentList(classId);
    }

    @Override
    public Call<ResponseNotice> createNotice(String class_Id, Notice notice) {
        return  api.createNotice(class_Id,notice);
    }

    @Override
    public Call<ResponseNotice> updateNotice(String noticeId, Notice notice) {
        return  api.updateNotice(noticeId,notice);
    }

    @Override
    public Call<ResponseNotice> getNotice(String noticeId) {
        return  api.getNotice(noticeId);
    }

    @Override
    public Call<ResponseGetNotice> getAllNotice(String classId) {
        return  api.getAllNotice(classId);
    }

    @Override
    public Call<ResDeleteNotice> deleteNotice(String noticeId, String classId) {
        return api.deleteNotice(noticeId,classId);
    }

}
