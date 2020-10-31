package com.attendo.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.attendo.api.ApiHelper;

public class appPref implements  appPrefHelper {

    private Context context;
    private  static appPref instance;

    public static final String TOKEN_KEY = "token";
    public static final String TIMESTAMP_KEY = "timestamp";


    private SharedPreferences sharedPrefs;

    public appPref(Context context) {
        this.context=context;
        sharedPrefs= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static appPref getInstance(Context context){
        if(instance == null){
            synchronized (ApiHelper.class){
                if(instance == null){
                    instance = new appPref(context);
                }
            }
        }
        return instance;
    }







    @Override
    public void savetoken(String token) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(TOKEN_KEY , token);
        editor.apply();


    }

    @Override
    public void timeStamp(String timeStamp) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(TIMESTAMP_KEY, timeStamp);
        editor.apply();

    }

    @Override
    public Boolean removeToken() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(TOKEN_KEY);
        return  editor.commit();

    }

    @Override
    public Boolean removetimeStamp() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(TIMESTAMP_KEY);
        return  editor.commit();
    }

    @Override
    public String getToken() {
        return sharedPrefs.getString(TOKEN_KEY , "");
    }

    @Override
    public String gettimeStamp() {
        return sharedPrefs.getString(TIMESTAMP_KEY, "");
    }
}



