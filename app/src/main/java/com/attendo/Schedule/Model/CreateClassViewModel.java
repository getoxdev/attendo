package com.attendo.Schedule.Model;

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
    private MutableLiveData<Response> classResponse;
    public CreateClassViewModel(@NonNull Application application) {
        super(application);
        apiHelper = new ApiHelper(application);
        classResponse=new MutableLiveData<Response>();
    }
    public MutableLiveData<Response> getClassResponse(){
        return classResponse;
    }
    public void setClassData(CreateClass createClass){
        apiHelper.createclass(createClass).enqueue(new Callback<CreateClass>() {
            @Override
            public void onResponse(Call<CreateClass> call, retrofit2.Response<CreateClass> response) {
                if(response.code()<300) {
                    // response1=createClass.getClass();
                    //createClass.postValue(response1);
                    //Log.i("response",Integer.toString(response.code()));
                    //Log.i("ID",createClass.getClass().getName());
                }
                else if(response.code()>=400) {
                    classResponse.postValue(null);
                    Log.i("responseNext", Integer.toString(response.code()));
                }
            }
            @Override
            public void onFailure(Call<CreateClass> call, Throwable t) {
                classResponse.postValue(null);
            }
        });
    }

}
