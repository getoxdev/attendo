package com.attendo.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Days
{
   @SerializedName("monday")
    private ArrayList<ClassDetail> monday;

   @SerializedName("tuesday")
    private ArrayList<ClassDetail> tuesday;

   @SerializedName("wednesday")
    private ArrayList<ClassDetail> wednesday;

   @SerializedName("thursday")
    private ArrayList<ClassDetail> thursday;

    @SerializedName("friday")
    private ArrayList<ClassDetail> friday;

    @SerializedName("saturday")
    private ArrayList<ClassDetail>  saturday;

    @SerializedName("sunday")
    private ArrayList<ClassDetail> sunday;

    public Days(ArrayList<ClassDetail> monday, ArrayList<ClassDetail> tuesday, ArrayList<ClassDetail> wednesday, ArrayList<ClassDetail> thursday, ArrayList<ClassDetail> friday, ArrayList<ClassDetail> saturday, ArrayList<ClassDetail> sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public ArrayList<ClassDetail> getMonday() {
        return monday;
    }

    public void setMonday(ArrayList<ClassDetail> monday) {
        this.monday = monday;
    }

    public ArrayList<ClassDetail> getTuesday() {
        return tuesday;
    }

    public void setTuesday(ArrayList<ClassDetail> tuesday) {
        this.tuesday = tuesday;
    }

    public ArrayList<ClassDetail> getWednesday() {
        return wednesday;
    }

    public void setWednesday(ArrayList<ClassDetail> wednesday) {
        this.wednesday = wednesday;
    }

    public ArrayList<ClassDetail> getThursday() {
        return thursday;
    }

    public void setThursday(ArrayList<ClassDetail> thursday) {
        this.thursday = thursday;
    }

    public ArrayList<ClassDetail> getFriday() {
        return friday;
    }

    public void setFriday(ArrayList<ClassDetail> friday) {
        this.friday = friday;
    }

    public ArrayList<ClassDetail> getSaturday() {
        return saturday;
    }

    public void setSaturday(ArrayList<ClassDetail> saturday) {
        this.saturday = saturday;
    }

    public ArrayList<ClassDetail> getSunday() {
        return sunday;
    }

    public void setSunday(ArrayList<ClassDetail> sunday) {
        this.sunday = sunday;
    }
}
