package com.attendo.Schedule.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.attendo.data.api.ApiHelper;

public class sharedpre implements sharedpreHelper{

    private Context context;
    private  static sharedpre instance;

    public static final String Class_ID_KEY = null;
    public static final String Class_Join_As_KEY = null;
    public static final String Class_Schedule_ID_KEY = null;

    private SharedPreferences sharedPrefs;

    public sharedpre(Context context) {
        this.context=context;
        sharedPrefs= PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static sharedpre getInstance(Context context){
        if(instance == null){
            synchronized (ApiHelper.class){
                if(instance == null){
                    instance = new sharedpre(context);
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
    public void AddClassJoinAs(String id) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Class_Join_As_KEY, id);
        editor.apply();
    }

    @Override
    public void AddClassScheduleId(String id) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Class_Schedule_ID_KEY, id);
        editor.apply();
    }

    @Override
    public String RetrieveClassId() {
        return sharedPrefs.getString(Class_ID_KEY , null);
    }

    @Override
    public String RetrieveClassJoinAs() {
        return sharedPrefs.getString(Class_Join_As_KEY , null);
    }

    @Override
    public String RetrieveClassScheduleId() {
        return sharedPrefs.getString(Class_Schedule_ID_KEY , null);
    }
}
