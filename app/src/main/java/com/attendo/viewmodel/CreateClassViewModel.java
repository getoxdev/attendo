package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.ResponseClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateClassViewModel extends AndroidViewModel {
    private ApiHelper apiHelper;
    private MutableLiveData<ResponseClass> classResponse;
    public CreateClassViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse=new MutableLiveData<ResponseClass>();
    }
    public MutableLiveData<ResponseClass> getClassResponse(){
        return classResponse;
    }

//    public void setClassData(CreateClass createClass){
//        apiHelper.createclass(createClass).enqueue(new Callback<CreateClass>() {
//            @Override
//            public void onResponse(Call<CreateClass> call, retrofit2.Response<ResponseClass> response) {
//                if(response.code()<300) {
//                    ResponseClass createClass1 = response.body();
//                    classResponse.postValue(createClass1);
//                }
//                else if(response.code()>=400) {
//                    classResponse.postValue(null);
//
//                }
//            }
//            @Override
//            public void onFailure(Call<CreateClass> call, Throwable t) {
//                classResponse.postValue(null);
//            }
//        });
//    }

    public void setClassResponse(CreateClass createClass){
        apiHelper.createclass(createClass).enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                if(response.code() < 300){
                    ResponseClass responseClass = response.body();
                    classResponse.postValue(responseClass);
                }else if(response.code() >= 400){
                    classResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                classResponse.postValue(null);
            }
        });
    }

}
