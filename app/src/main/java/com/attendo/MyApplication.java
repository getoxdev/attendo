package com.attendo;

import android.app.Application;

import com.attendo.api.ApiHelper;

public class MyApplication extends Application {
    private appDataManager DataManager;

    public  appDataManager getDataManager(){
        if(DataManager == null){
            synchronized (ApiHelper.class){
                if(DataManager == null){
                    DataManager = new appDataManager(this);
                }
            }
        }
        return DataManager;
    }

}
