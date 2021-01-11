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

}
