package com.attendo.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.JoinClass;
import com.attendo.data.model.ResponseJoinClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinClassViewModel extends AndroidViewModel {

    private ApiHelper apiHelper;
    private MutableLiveData<ResponseJoinClass> joinResponse;

    public JoinClassViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        joinResponse=new MutableLiveData<ResponseJoinClass>();
    }

    public MutableLiveData<ResponseJoinClass> getJoinResponse(){
        return joinResponse;
    }

//    public void setClassData(JoinClass joinClass){
//        apiHelper.joinclass(joinClass).enqueue(new Callback<JoinClass>() {
//            @Override
//            public void onResponse(Call<JoinClass> call, Response<JoinClass> response) {
//                if(response.code()<300) {
//
//                    JoinClass joinClass1 = response.body();
//                    joinResponse.postValue(joinClass1);
//
//                }
//                else if(response.code()>=400) {
//                    joinResponse.postValue(null);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JoinClass> call, Throwable t) {
//                joinResponse.postValue(null);
//            }
//        });
//    }

    public void setJoinResponse(JoinClass joinClass){
        apiHelper.joinclass(joinClass).enqueue(new Callback<ResponseJoinClass>() {
            @Override
            public void onResponse(Call<ResponseJoinClass> call, Response<ResponseJoinClass> response) {
                if(response.code() < 300){
                    ResponseJoinClass responseJoinClass = response.body();
                    joinResponse.postValue(responseJoinClass);
                }else if(response.code() >= 400){
                    joinResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseJoinClass> call, Throwable t) {
                joinResponse.postValue(null);

            }
        });
    }

}
