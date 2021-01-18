package com.attendo.Schedule.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.attendo.data.api.ApiHelper;

public class AppPreferences implements SharedPreferencesHelper {

    private Context context;
    private  static AppPreferences instance;

    public static final String Class_ID_KEY = null;
    public static final String Class_Join_As_KEY = null;
    public static final String Class_Schedule_ID_KEY = null;

    private SharedPreferences sharedPrefs;

    public AppPreferences(Context context) {
        this.context=context;
        sharedPrefs= PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static AppPreferences getInstance(Context context){
        if(instance == null){
            instance = new AppPreferences(context);
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
