package com.attendo.data.model;

public class ScheduleEdit {

    private String scheduleId;
    private String scheduleClassId;
    private String time;
    private String subject;


    public ScheduleEdit(String scheduleId, String scheduleClassId, String time, String subject) {
        this.scheduleId = scheduleId;
        this.scheduleClassId = scheduleClassId;
        this.time = time;
        this.subject = subject;
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
