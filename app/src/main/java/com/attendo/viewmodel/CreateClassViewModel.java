package com.attendo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.ResponseCreateClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateClassViewModel extends AndroidViewModel {
    private ApiHelper apiHelper;
    private MutableLiveData<ResponseCreateClass> classResponse;
    public CreateClassViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse=new MutableLiveData<ResponseCreateClass>();
    }
    public MutableLiveData<ResponseCreateClass> getClassResponse(){
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
        apiHelper.createclass(createClass).enqueue(new Callback<ResponseCreateClass>() {
            @Override
            public void onResponse(Call<ResponseCreateClass> call, Response<ResponseCreateClass> response) {
                if(response.code() < 300){
                    ResponseCreateClass responseCreateClass = response.body();
                    classResponse.postValue(responseCreateClass);
                }else if(response.code() >= 400){
                    classResponse.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseCreateClass> call, Throwable t) {
                classResponse.postValue(null);
            }
        });
    }

}
