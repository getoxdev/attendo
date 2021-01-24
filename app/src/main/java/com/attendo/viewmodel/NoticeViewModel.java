package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.schedule.Notice;
import com.attendo.data.model.schedule.ResDeleteNotice;
import com.attendo.data.model.schedule.ResponseCreateClass;
import com.attendo.data.model.schedule.ResponseGetNotice;
import com.attendo.data.model.schedule.ResponseGetSchedule;
import com.attendo.data.model.schedule.ResponseJoinClass;
import com.attendo.data.model.schedule.ResponseNotice;
import com.attendo.data.model.schedule.ResponseSchedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeViewModel extends AndroidViewModel {
    private ApiHelper apiHelper;
    private MutableLiveData<ResponseNotice> noticeResponse;
    private MutableLiveData<ResponseGetNotice> getNoticeRes;
    private MutableLiveData<ResDeleteNotice> deleteRes;


    public NoticeViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        noticeResponse = new MutableLiveData<ResponseNotice>();
        getNoticeRes = new MutableLiveData<ResponseGetNotice>();
        deleteRes = new MutableLiveData<ResDeleteNotice>();
    }

    public  MutableLiveData<ResponseNotice> get_Notice_Response()
    {
        return noticeResponse;
    }

    public MutableLiveData<ResponseGetNotice> get_all_notice(){ return getNoticeRes; }

    public  MutableLiveData<ResDeleteNotice> getDeleteResponse() { return  deleteRes; }

    public void create_Notice(String Class_id, Notice notice)
    {
        apiHelper.createNotice(Class_id,notice).enqueue(new Callback<ResponseNotice>() {
            @Override
            public void onResponse(Call<ResponseNotice> call, Response<ResponseNotice> response) {
                if(response.code()<300){
                    get_Notice_Response().postValue(response.body());

                }else if(response.code()>=400){
                    get_Notice_Response().postValue(null);

                }

            }

            @Override
            public void onFailure(Call<ResponseNotice> call, Throwable t) {
                get_Notice_Response().postValue(null);


            }
        });
    }

    public void get_notice(String noticeID)
    {
        apiHelper.getNotice(noticeID).enqueue(new Callback<ResponseNotice>() {
            @Override
            public void onResponse(Call<ResponseNotice> call, Response<ResponseNotice> response) {
                if(response.code()<300){
                    get_Notice_Response().postValue(response.body());

                }else if(response.code()>=400){
                    get_Notice_Response().postValue(null);

                }
            }

            @Override
            public void onFailure(Call<ResponseNotice> call, Throwable t) {
                get_Notice_Response().postValue(null);

            }
        });
    }

    public void get_All_notice(String classId)
    {
        apiHelper.getAllNotice(classId).enqueue(new Callback<ResponseGetNotice>() {
            @Override
            public void onResponse(Call<ResponseGetNotice> call, Response<ResponseGetNotice> response) {
                if(response.code()<300){
                    get_all_notice().postValue(response.body());

                }else if(response.code()>=400){
                    get_all_notice().postValue(null);

                }
            }

            @Override
            public void onFailure(Call<ResponseGetNotice> call, Throwable t) {
                get_all_notice().postValue(null);

            }
        });
    }

    public void edit_notice(String NoticeId,Notice notice)
    {
        apiHelper.updateNotice(NoticeId,notice).enqueue(new Callback<ResponseNotice>() {
            @Override
            public void onResponse(Call<ResponseNotice> call, Response<ResponseNotice> response) {
                if(response.code()<300){
                    get_Notice_Response().postValue(response.body());

                }else if(response.code()>=400){
                    get_Notice_Response().postValue(null);

                }
            }

            @Override
            public void onFailure(Call<ResponseNotice> call, Throwable t) {
                get_Notice_Response().postValue(null);

            }
        });
    }

    public  void Delete_notice(String notice_id,String class_id)
    {
        apiHelper.deleteNotice(notice_id,class_id).enqueue(new Callback<ResDeleteNotice>() {
            @Override
            public void onResponse(Call<ResDeleteNotice> call, Response<ResDeleteNotice> response) {
                if(response.code()<300){
                    getDeleteResponse().postValue(response.body());

                }else if(response.code()>=400){
                   getDeleteResponse().postValue(null);

                }
            }

            @Override
            public void onFailure(Call<ResDeleteNotice> call, Throwable t) {
                getDeleteResponse().postValue(null);

            }
        });
    }



}
