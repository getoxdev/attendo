package com.attendo.data.model;

public class ScheduleEdit {

    private String scheduleId;
    private String scheduleClassId;
    private String day;
    private String time;
    private String subject;
    private String faculty;

    public ScheduleEdit(String scheduleId, String scheduleClassId, String day, String time, String subject, String faculty) {
        this.scheduleId = scheduleId;
        this.scheduleClassId = scheduleClassId;
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.faculty = faculty;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleClassId() {
        return scheduleClassId;
    }

    public void setScheduleClassId(String scheduleClassId) {
        this.scheduleClassId = scheduleClassId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
