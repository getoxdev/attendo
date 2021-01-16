package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.JoinClass;
import com.attendo.data.model.ResponseCreateClass;
import com.attendo.data.model.ResponseGetSchedule;
import com.attendo.data.model.ResponseJoinClass;
import com.attendo.data.model.ResponseSchedule;
import com.attendo.data.model.Schedule;
import com.attendo.data.model.ScheduleEdit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends AndroidViewModel {
      private ApiHelper apiHelper;
      private MutableLiveData<ResponseCreateClass> classResponse;
      private MutableLiveData<ResponseJoinClass> joinResponse;
      private MutableLiveData<ResponseSchedule> scheduleResponse ,editResponse;
      private MutableLiveData<ResponseGetSchedule> getScheduleResponse;

      public ScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse = new MutableLiveData<ResponseCreateClass>();
        joinResponse = new MutableLiveData<ResponseJoinClass>();
        scheduleResponse = new MutableLiveData<ResponseSchedule>();
        editResponse=new MutableLiveData<ResponseSchedule>();
        getScheduleResponse = new MutableLiveData<ResponseGetSchedule>();
    }

    public MutableLiveData<ResponseCreateClass> getClassResponse(){
        return classResponse;
    }

    public MutableLiveData<ResponseSchedule> getScheduleResponse(){
        return scheduleResponse;
    }

    public MutableLiveData<ResponseJoinClass> getJoinResponse(){
        return joinResponse;
    }

    public MutableLiveData<ResponseGetSchedule> getScheduleGetResponse(){
        return getScheduleResponse;
    }

    public MutableLiveData<ResponseSchedule> editScheduleResponse(){
        return editResponse;
    }

    //CREATE CLASS
    public void setClassResponse(CreateClass createClass){
        apiHelper.createclass(createClass).enqueue(new Callback<ResponseCreateClass>() {
            @Override
            public void onResponse(Call<ResponseCreateClass> call, Response<ResponseCreateClass> response) {
                if(response.code()==201||response.code()==200){
                    ResponseCreateClass responseCreateClass = response.body();
                    classResponse.postValue(responseCreateClass);
                }else if(response.code()==400||response.code()==404){
                    classResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseCreateClass> call, Throwable t) {
                classResponse.postValue(null);
            }
        });
    }

    //JOIN CLASS
    public void setJoinResponse(JoinClass joinClass){
        apiHelper.joinclass(joinClass).enqueue(new Callback<ResponseJoinClass>() {
            @Override
            public void onResponse(Call<ResponseJoinClass> call, Response<ResponseJoinClass> response) {
                if(response.code()==200 || response.code()==201){
                    ResponseJoinClass responseJoinClass = response.body();
                    joinResponse.postValue(responseJoinClass);
                }else if(response.code() == 400||response.code()==404){
                    joinResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseJoinClass> call, Throwable t) {
                joinResponse.postValue(null);

            }
        });
    }

    // SET SCHEDULE
    public void setScheduleResponse(Schedule schedule){
        apiHelper.createschedule(schedule).enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                if(response.code()==200 || response.code()==201){
                    scheduleResponse.postValue(response.body());
                    Log.e("onResponse: ", ""+response.code());
                }else if(response.code()==400 || response.code()==404){
                    Log.e("onResponse: ", ""+response.code());
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

    //GET SCHEDULE
    public void setScheduleGetResponse(String classId, String day){
        apiHelper.getschedule(classId, day).enqueue(new Callback<ResponseGetSchedule>() {
            @Override
            public void onResponse(Call<ResponseGetSchedule> call, Response<ResponseGetSchedule> response) {
                if(response.code()<300){
                    getScheduleResponse.postValue(response.body());
                    Log.e("Get Schedule 300",response.message());
                }else if(response.code()>=400){
                    getScheduleResponse.postValue(null);
                    Log.e("Get Schedule 400",response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseGetSchedule> call, Throwable t) {
                Log.e("Get Schedule Error",t.getMessage());
                getScheduleResponse.postValue(null);
            }
        });
    }


    //EDIT SCHEDULE RESPONSE
    public void editScheduleResponse(ScheduleEdit scheduleEdit){
        apiHelper.updateschedule(scheduleEdit).enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                if(response.code()==200||response.code()==201){
                    ResponseSchedule responseSchedule = response.body();
                    editResponse.postValue(responseSchedule);
                }else if(response.code()==400||response.code()==404){
                    editResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable t) {
                editResponse.postValue(null);
            }
        });
    }

}
