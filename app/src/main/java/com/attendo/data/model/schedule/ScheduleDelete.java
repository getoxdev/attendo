package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleDelete {

    @SerializedName("scheduleId")
    @Expose
    private String scheduleId;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("scheduleClassId")
    @Expose
    private String scheduleClassId;


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
