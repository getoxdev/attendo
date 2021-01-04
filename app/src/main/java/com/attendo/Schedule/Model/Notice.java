package com.attendo.Schedule.Model;

public class Notice {

    String subject;
    String detail;

    public Notice(String subject, String detail) {
        this.subject = subject;
        this.detail = detail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
