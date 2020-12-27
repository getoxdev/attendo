package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.Response;

import retrofit2.Call;
import retrofit2.Callback;
public class CreateClassViewModel extends AndroidViewModel {
    private ApiHelper apiHelper;
    private MutableLiveData<CreateClass> classResponse;
    public CreateClassViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse=new MutableLiveData<CreateClass>();
    }
    public MutableLiveData<CreateClass> getClassResponse(){
        return classResponse;
    }

    public void setClassData(CreateClass createClass){
        apiHelper.createclass(createClass).enqueue(new Callback<CreateClass>() {
            @Override
            public void onResponse(Call<CreateClass> call, retrofit2.Response<CreateClass> response) {
                if(response.code()<300) {

                    CreateClass createClass1 = response.body();
                    classResponse.postValue(createClass1);

                }
                else if(response.code()>=400) {
                    classResponse.postValue(null);

                }
            }
            @Override
            public void onFailure(Call<CreateClass> call, Throwable t) {
                classResponse.postValue(null);
            }
        });
    }

}
