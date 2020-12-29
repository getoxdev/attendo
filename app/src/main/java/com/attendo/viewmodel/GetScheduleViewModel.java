package com.attendo.viewmodel;

import android.app.Application;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.GetSchedule;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetScheduleViewModel extends AndroidViewModel {

    private ApiHelper apiHelper;
    private MutableLiveData<ResponseSchedule> scheduleResponse;

    public GetScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        scheduleResponse=new MutableLiveData<ResponseSchedule>();
    }

    public MutableLiveData<ResponseSchedule> getScheduleResponse(){
        return scheduleResponse;
    }

    public void setScheduleResponse( GetSchedule getSchedule){
        apiHelper.getschedule(getSchedule).enqueue(new Callback<ResponseSchedule>() {
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
