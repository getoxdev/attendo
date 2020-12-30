package com.attendo.data.model;

import java.util.List;

public class Classes {

    private List<SubjectDetails> monday;
    private List<SubjectDetails> tuesday;
    private List<SubjectDetails> wednesday;
    private List<SubjectDetails> thursday;
    private List<SubjectDetails> friday;
    private List<SubjectDetails> saturday;
    private List<SubjectDetails> sunday;

    public Classes(List<SubjectDetails> monday, List<SubjectDetails> tuesday, List<SubjectDetails> wednesday, List<SubjectDetails> thursday, List<SubjectDetails> friday, List<SubjectDetails> saturday, List<SubjectDetails> sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public List<SubjectDetails> getMonday() {
        return monday;
    }

    public void setMonday(List<SubjectDetails> monday) {
        this.monday = monday;
    }

    public List<SubjectDetails> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<SubjectDetails> tuesday) {
        this.tuesday = tuesday;
    }

    public List<SubjectDetails> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<SubjectDetails> wednesday) {
        this.wednesday = wednesday;
    }

    public List<SubjectDetails> getThursday() {
        return thursday;
    }

    public void setThursday(List<SubjectDetails> thursday) {
        this.thursday = thursday;
    }

    public List<SubjectDetails> getFriday() {
        return friday;
    }

    public void setFriday(List<SubjectDetails> friday) {
        this.friday = friday;
    }

    public List<SubjectDetails> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<SubjectDetails> saturday) {
        this.saturday = saturday;
    }

    public List<SubjectDetails> getSunday() {
        return sunday;
    }

    public void setSunday(List<SubjectDetails> sunday) {
        this.sunday = sunday;
    }
}
