package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectDetails {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("faculty")
    @Expose
    private String faculty;

    public SubjectDetails(String _id, String time, String subject, String faculty) {
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
