package com.attendo.data.model;

public class ScheduleDelete {

    private String scheduleId;
    private String scheduleClassId;
    private String day;

    public ScheduleDelete(String scheduleId, String day,String scheduleClassId) {
        this.scheduleId = scheduleId;
        this.day = day;
        this.scheduleClassId = scheduleClassId;

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
}
