package com.attendo.data.model;

public class Classes {

    private WeekDay monday;
    private WeekDay tuesday;
    private WeekDay wednesday;
    private WeekDay thursday;
    private WeekDay friday;
    private WeekDay saturday;
    private WeekDay sunday;

    public Classes(WeekDay monday, WeekDay tuesday, WeekDay wednesday, WeekDay thursday, WeekDay friday, WeekDay saturday, WeekDay sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public WeekDay getMonday() {
        return monday;
    }

    public void setMonday(WeekDay monday) {
        this.monday = monday;
    }

    public WeekDay getTuesday() {
        return tuesday;
    }

    public void setTuesday(WeekDay tuesday) {
        this.tuesday = tuesday;
    }

    public WeekDay getWednesday() {
        return wednesday;
    }

    public void setWednesday(WeekDay wednesday) {
        this.wednesday = wednesday;
    }

    public WeekDay getThursday() {
        return thursday;
    }

    public void setThursday(WeekDay thursday) {
        this.thursday = thursday;
    }

    public WeekDay getFriday() {
        return friday;
    }

    public void setFriday(WeekDay friday) {
        this.friday = friday;
    }

    public WeekDay getSaturday() {
        return saturday;
    }

    public void setSaturday(WeekDay saturday) {
        this.saturday = saturday;
    }

    public WeekDay getSunday() {
        return sunday;
    }

    public void setSunday(WeekDay sunday) {
        this.sunday = sunday;
    }
}
