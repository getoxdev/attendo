package com.attendo.Schedule.Preference;

public interface sharedpreHelper {

    void AddClassId(String id);
    void AddClassJoinAs(String id);
    void AddClassScheduleId(String id);

    String RetrieveClassId();
    String RetrieveClassJoinAs();
    String RetrieveClassScheduleId();
}
