package com.attendo.viewmodel;

import android.app.Application;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.GetSchedule;
import com.attendo.data.model.ResponseGetSchedule;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetScheduleViewModel extends AndroidViewModel {

    private ApiHelper apiHelper;
    private MutableLiveData<ResponseGetSchedule> scheduleResponse;

    public GetScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        scheduleResponse=new MutableLiveData<ResponseGetSchedule>();
    }

    public MutableLiveData<ResponseGetSchedule> getScheduleGetResponse(){
        return scheduleResponse;
    }

    public void setScheduleGetResponse(String classId, String day){
        apiHelper.getschedule(classId, day).enqueue(new Callback<ResponseGetSchedule>() {
            @Override
            public void onResponse(Call<ResponseGetSchedule> call, Response<ResponseGetSchedule> response) {
                if(response.code() == 200 || response.code() == 201){
                    scheduleResponse.postValue(response.body());
                }else if(response.code() == 400 || response.code() == 404){
                    scheduleResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseGetSchedule> call, Throwable t) {
                scheduleResponse.postValue(null);
            }
        });
    }


}
