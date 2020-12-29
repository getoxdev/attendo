package com.attendo.data.model;

public class Schedule {

    private String classId;
    private String day;
    private String date;
    private String time;
    private String subject;

    public Schedule(String classId, String day, String date, String time, String subject) {
        this.classId = classId;
        this.day = day;
        this.date = date;
        this.time = time;
        this.subject = subject;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
