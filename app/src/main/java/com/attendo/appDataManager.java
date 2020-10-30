package com.attendo;

import android.content.Context;

import com.attendo.api.ApiHelper;
import com.attendo.model.Reminder;
import com.attendo.pref.appPref;

import retrofit2.Call;

public class appDataManager implements appDataManagerHelper {

    private Context context;
    private ApiHelper apiHelper;
    private appPref appPreferences;

    public appDataManager(Context context) {
        this.context = context;
        apiHelper = ApiHelper.getInstance(context);
        appPreferences = appPref.getInstance(context);
    }




    @Override
    public Call<Reminder> setReminder(Reminder reminder) {
        return apiHelper.setReminder(reminder);
    }

    @Override
    public void savetoken(String token) {
        appPreferences.savetoken(token);

    }

    @Override
    public void timeStamp(String timeStamp) {
        appPreferences.timeStamp(timeStamp);

    }

    @Override
    public Boolean removeToken() {
        return appPreferences.removeToken();
    }

    @Override
    public Boolean removetimeStamp() {
        return appPreferences.removetimeStamp();
    }

    @Override
    public String getToken() {
        return appPreferences.getToken();
    }

    @Override
    public String gettimeStamp() {
        return appPreferences.gettimeStamp();
    }

}
