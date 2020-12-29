package com.attendo.data.model;

public class GetSchedule {
    private String ClassId;
    private String date;

    public GetSchedule(String classId, String date) {
        ClassId = classId;
        this.date = date;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
