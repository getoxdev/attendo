package com.attendo.data.model;

import com.google.gson.annotations.SerializedName;

public class ClassDetail
{
    @SerializedName("subject")
    private String subject;

    @SerializedName("time")
    private String time;

    @SerializedName("teacher")
    private String teacher;

    @SerializedName("status")
    private String status;

    @SerializedName("notice")
    private String notice;

    public ClassDetail(String subject, String time, String teacher, String status, String notice)
    {
        this.subject = subject;
        this.time = time;
        this.teacher = teacher;
        this.status = status;
        this.notice = notice;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
