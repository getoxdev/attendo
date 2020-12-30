package com.attendo.data.model;

public class ResponseGetSchedule {

    private String status;
    private WeekDay requiredSchedule;

    public ResponseGetSchedule(String status, WeekDay requiredSchedule) {
        this.status = status;
        this.requiredSchedule = requiredSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WeekDay getRequiredSchedule() {
        return requiredSchedule;
    }

    public void setRequiredSchedule(WeekDay requiredSchedule) {
        this.requiredSchedule = requiredSchedule;
    }
}
