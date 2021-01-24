package com.attendo.data.model.schedule;

public class Schedule {

    private String classId;
    private String day;
    private String time;
    private String subject;
    private String faculty;


    public Schedule(String classId, String day, String time, String subject, String faculty) {
        this.classId = classId;
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.faculty = faculty;
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
