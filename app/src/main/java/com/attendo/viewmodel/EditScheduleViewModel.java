package com.attendo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;
import com.attendo.data.model.ScheduleEdit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditScheduleViewModel extends AndroidViewModel {

    private ApiHelper apiHelper;
    private MutableLiveData<ResponseSchedule> scheduleResponse;

    public EditScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        scheduleResponse=new MutableLiveData<ResponseSchedule>();
    }

    public MutableLiveData<ResponseSchedule> getScheduleResponse(){
        return scheduleResponse;
    }

    public void setScheduleResponse(ScheduleEdit scheduleEdit){
        apiHelper.updateschedule(scheduleEdit).enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                if(response.code() < 300){
                    ResponseSchedule responseSchedule = response.body();
                    scheduleResponse.postValue(responseSchedule);
                }else if(response.code() >= 400){
                    scheduleResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable t) {
                scheduleResponse.postValue(null);
            }
        });
    }

}
