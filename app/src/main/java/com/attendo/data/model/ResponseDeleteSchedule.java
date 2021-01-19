package com.attendo.data.model;

public class ResponseDeleteSchedule {

    private String status;
    private String message;
    private schedule_list schedule;

    public ResponseDeleteSchedule(String status, String message, schedule_list schedule) {
        this.status = status;
        this.message = message;
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public schedule_list getSchedule() {
        return schedule;
    }

    public void setSchedule(schedule_list schedule) {
        this.schedule = schedule;
    }
}
