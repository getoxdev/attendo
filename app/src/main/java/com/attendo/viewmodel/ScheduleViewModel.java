package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.schedule.CreateClass;
import com.attendo.data.model.schedule.FcmToken;
import com.attendo.data.model.schedule.GetStudentListResponse;
import com.attendo.data.model.schedule.JoinClass;
import com.attendo.data.model.schedule.ResLeaveClass;
import com.attendo.data.model.schedule.ResponseCreateClass;
import com.attendo.data.model.schedule.ResponseDeleteSchedule;
import com.attendo.data.model.schedule.ResponseFcm;
import com.attendo.data.model.schedule.ResponseGetSchedule;
import com.attendo.data.model.schedule.ResponseJoinClass;
import com.attendo.data.model.schedule.ResponseSchedule;
import com.attendo.data.model.schedule.ResponseUser;
import com.attendo.data.model.schedule.Schedule;
import com.attendo.data.model.schedule.ScheduleDelete;
import com.attendo.data.model.schedule.ScheduleEdit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends AndroidViewModel
{
      private ApiHelper apiHelper;
      private MutableLiveData<ResponseCreateClass> classResponse;
      private MutableLiveData<ResponseJoinClass> joinResponse;
      private MutableLiveData<ResponseSchedule> scheduleResponse ,editResponse;
      private MutableLiveData<ResponseGetSchedule> getScheduleResponse;
      private MutableLiveData<ResponseDeleteSchedule> deleteResponse;
      private MutableLiveData<GetStudentListResponse> studentListResponse;
      private MutableLiveData<ResLeaveClass> leave_class;
      private MutableLiveData<ResponseFcm> getFcm;
      private MutableLiveData<ResponseUser> updateFcm;



      public ScheduleViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse = new MutableLiveData<ResponseCreateClass>();
        joinResponse = new MutableLiveData<ResponseJoinClass>();
        scheduleResponse = new MutableLiveData<ResponseSchedule>();
        editResponse=new MutableLiveData<ResponseSchedule>();
        getScheduleResponse = new MutableLiveData<ResponseGetSchedule>();
        deleteResponse = new MutableLiveData<ResponseDeleteSchedule>();
        studentListResponse = new MutableLiveData<GetStudentListResponse>();
        leave_class = new MutableLiveData<ResLeaveClass>();
        getFcm = new MutableLiveData<ResponseFcm>();
        updateFcm = new MutableLiveData<ResponseUser>();

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

    public MutableLiveData<ResponseDeleteSchedule> getDeleteResponse(){
          return  deleteResponse;
    }

    public MutableLiveData<ResponseSchedule> scheduleResponseEdit(){
        return editResponse;
    }

    public MutableLiveData<GetStudentListResponse> getStudentListResponse(){
          return studentListResponse;
    }

    public MutableLiveData<ResLeaveClass> leaveClassResponse() { return  leave_class; }

    public  MutableLiveData<ResponseFcm> getFcmResponse()
    {
        return  getFcm;
    }

    public MutableLiveData<ResponseUser> updateFcmResponse()
    {
        return  updateFcm;
    }


    //GET FCM
    public  void getFcm(String email)
    {
        apiHelper.getFcmToken(email).enqueue(new Callback<ResponseFcm>() {
            @Override
            public void onResponse(Call<ResponseFcm> call, Response<ResponseFcm> response) {
                if(response.code()<300){
                    getFcm.postValue(response.body());
                }else if(response.code()>400){
                    Log.i("Response code: ",String.valueOf(response.code()));
                    getFcm.postValue(null);
                }


            }

            @Override
            public void onFailure(Call<ResponseFcm> call, Throwable t) {
                getFcm.postValue(null);
            }
        });
    }

    //UPDATE FCM TOKEN
     public void updateFcm(FcmToken fcmToken)
     {
         apiHelper.updateFcmToken(fcmToken).enqueue(new Callback<ResponseUser>() {
             @Override
             public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                 if(response.code()<300){
                     updateFcm.postValue(response.body());
                 }else if(response.code()>=400){
                     Log.i("Response code: ",String.valueOf(response.code()));
                     updateFcm.postValue(null);
                 }
             }

             @Override
             public void onFailure(Call<ResponseUser> call, Throwable t) {
                 updateFcm.postValue(null);
             }
         });
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
                    Log.i("Response code: ",String.valueOf(response.code()));
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
                    joinResponse.postValue(response.body());
                }else if(response.code() == 400||response.code()==404){
                    Log.i("Response code: ",String.valueOf(response.code()));
                    joinResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseJoinClass> call, Throwable t) {
                joinResponse.postValue(null);

            }
        });
    }

    //LEAVE CLASS
    public void leaveClass(String email)
    {
        apiHelper.leaveClass(email).enqueue(new Callback<ResLeaveClass>() {
            @Override
            public void onResponse(Call<ResLeaveClass> call, Response<ResLeaveClass> response) {
                if(response.code()<300){
                    leave_class.postValue(response.body());
                }else if(response.code()>400){
                    leave_class.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResLeaveClass> call, Throwable t) {
                leave_class.postValue(null);

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
                    Log.e("Get_Schedule",response.message()+" data is there success");
                }else if(response.code()>=400){
                    getScheduleResponse.postValue(null);
                    Log.e("Get_Schedule",response.message()+response.code()+ "no data bad request");
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
                    editResponse.postValue(response.body());
                    Log.e("edit schedule",response.message()+response.code()+"succesfull");
                }else if(response.code()==400||response.code()==404){
                    editResponse.postValue(null);
                    Log.e("edit schedule",response.message()+response.code()+"fail");

                }

            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable t) {
                editResponse.postValue(null);
            }
        });
    }

    //DELETE SCHEDULE
    public void DeleteSchedule(ScheduleDelete scheduleDelete)
    {
        apiHelper.DeleteSchedule(scheduleDelete).enqueue(new Callback<ResponseDeleteSchedule>() {
            @Override
            public void onResponse(Call<ResponseDeleteSchedule> call, Response<ResponseDeleteSchedule> response) {
                if(response.code()==200||response.code()==201){
                    deleteResponse.postValue(response.body());
                    Log.e("delete schedule",response.message()+response.code()+"succesfull");

                }else if(response.code()==400||response.code()==404){
                    deleteResponse.postValue(null);
                    Log.e("delete schedule",response.message()+response.code()+"fail");
                }
            }

            @Override
            public void onFailure(Call<ResponseDeleteSchedule> call, Throwable t) {
                deleteResponse.postValue(null);

            }
        });
    }


    //get student list in a class
    public void GetStudentList(String classId){
          apiHelper.getStudentList(classId).enqueue(new Callback<GetStudentListResponse>() {
              @Override
              public void onResponse(Call<GetStudentListResponse> call, Response<GetStudentListResponse> response) {
                  if(response.code() == 200 || response.code() == 201){
                      studentListResponse.postValue(response.body());
                      Log.e("getStudentList", response.message() + response.code() + " : successful");
                  }else if(response.code() == 400 || response.code() == 404){
                      studentListResponse.postValue(null);
                      Log.e("getStudentList" , response.message() + response.code() + " : failed");
                  }
              }

              @Override
              public void onFailure(Call<GetStudentListResponse> call, Throwable t) {
                    studentListResponse.postValue(null);
              }
          });
    }
}

