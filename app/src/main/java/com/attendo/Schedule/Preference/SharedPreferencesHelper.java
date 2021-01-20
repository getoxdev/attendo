package com.attendo.Schedule.Preference;

public interface SharedPreferencesHelper {

    void AddClassId(String id);
    void AddJoinAs(String join);
    void AddClassScheduleId(String id);
    void AddScheduleClassId(String scheduleclassid);

    String RetrieveClassId();
    String RetrieveClassScheduleId();
    String RetrieveJoinAs();
    String RetrieveScheduleClassId();
}
