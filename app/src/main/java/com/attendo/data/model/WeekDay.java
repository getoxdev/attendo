package com.attendo.data.model;

public class WeekDay {

    private String _id;
    private String time;
    private String subject;
    private String faculty;

    public WeekDay(String _id, String time, String subject, String faculty) {
        this._id = _id;
        this.time = time;
        this.subject = subject;
        this.faculty = faculty;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
