package com.attendo.Schedule.Preference;

public interface SharedPreferencesHelper {

    void AddClassId(String id);
    void AddJoinAs(String join);
    void AddClassScheduleId(String id);
    void AddScheduleId(String ScId);
    void AddFcm(String fcm);

    String RetrieveClassId();
    String RetrieveClassScheduleId();
    String RetrieveJoinAs();
    String retrieveScheduleId();
    String RetrieveFcm();
}
