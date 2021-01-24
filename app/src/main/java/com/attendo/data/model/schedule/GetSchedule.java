package com.attendo.data.model.schedule;

public class GetSchedule {
    private String ClassId;
    private String day;

    public GetSchedule(String classId, String day) {
        ClassId = classId;
        this.day = day;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = day;
    }
}
