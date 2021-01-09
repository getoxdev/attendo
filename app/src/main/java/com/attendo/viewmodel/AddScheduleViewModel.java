package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.ResponseCreateClass;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddScheduleViewModel extends AndroidViewModel {

    private ApiHelper apiHelper;
    private MutableLiveData<ResponseSchedule> scheduleResponse;

    public AddScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        scheduleResponse=new MutableLiveData<ResponseSchedule>();
    }

    public MutableLiveData<ResponseSchedule> getScheduleResponse(){
        return scheduleResponse;
    }

    public void setScheduleResponse(Schedule schedule){
        apiHelper.createschedule(schedule).enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                if(response.code() < 300){
                    scheduleResponse.postValue(response.body());
                }else if(response.code() >= 400){
                    Log.e("onResponse: ", "NULL");
                    scheduleResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable t) {
                Log.e("onFailure: ",t.getMessage());
                scheduleResponse.postValue(null);
            }
        });
    }

}
