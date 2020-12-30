package com.attendo.data.model;

public class ResponseSchedule {

    private String status;
    private schedule_list schedule;

    public ResponseSchedule(String status, schedule_list schedule) {
        this.status = status;
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public schedule_list getSchedule() {
        return schedule;
    }

    public void setSchedule(schedule_list schedule) {
        this.schedule = schedule;
    }
}
