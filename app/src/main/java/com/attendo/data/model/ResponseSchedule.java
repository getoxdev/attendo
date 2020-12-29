package com.attendo.data.model;

public class ResponseSchedule {

    private String status;
    private Classes classes;
    private schedule_list schedule;
    private String __v;

    public ResponseSchedule(String status, Classes classes, schedule_list schedule, String __v) {
        this.status = status;
        this.classes = classes;
        this.schedule = schedule;
        this.__v = __v;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public schedule_list getSchedule() {
        return schedule;
    }

    public void setSchedule(schedule_list schedule) {
        this.schedule = schedule;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

}
