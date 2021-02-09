package com.attendo.Schedule.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.attendo.data.api.ApiHelper;

public class AppPreferences implements SharedPreferencesHelper {

    private Context context;
    private  static AppPreferences instance;

    public static final String Class_ID_KEY = null;
    public static final String Class_Schedule_Class_Id_KEY = null;
    public static final String Class_Schedule_ID_KEY = null;

    private SharedPreferences sharedPrefs;

    public AppPreferences(Context context) {
        this.context=context;
        sharedPrefs= PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static AppPreferences getInstance(Context context){
        if(instance == null){
            synchronized (AppPreferences.class){
                if(instance == null){
                    instance = new AppPreferences(context);
                }
            }

        }
        return instance;
    }


    @Override
    public void AddClassId(String id) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Class_ID_KEY, id);
        editor.apply();
    }


    @Override
    public void AddJoinAs(String join) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("CLASS_JOIN_AS", join);
        editor.apply();
    }

    @Override
    public void AddClassScheduleId(String id) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Class_Schedule_ID_KEY, id);
        editor.apply();
    }

    @Override
    public void AddScheduleId(String ScId) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("ClassScheduleId", ScId);
        editor.apply();
    }

    @Override
    public void AddFcm(String fcm) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("FCM", fcm);
        editor.apply();
    }


    @Override
    public String RetrieveClassId() {
        return sharedPrefs.getString(Class_ID_KEY , null);
    }

    @Override
    public String RetrieveClassScheduleId() {
        return sharedPrefs.getString(Class_Schedule_ID_KEY , null);
    }

    @Override
    public String RetrieveJoinAs() {
        return sharedPrefs.getString("CLASS_JOIN_AS" , "nothing");
    }

    @Override
    public String retrieveScheduleId() {
        return sharedPrefs.getString("ClassScheduleId" , null);
    }

    @Override
    public String RetrieveFcm() {
        return sharedPrefs.getString("FCM" , null);
    }

}
