package com.attendo.data.model.reminder;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Reminder
{
    @SerializedName("token")
    private String token;

    @SerializedName("time")
    private String time;

    @SerializedName("label")
    private String label;

    @SerializedName("state")
    private Boolean state;

    @SerializedName("_id")
    private  String _id;


    public Reminder(String token, String time, String label, Boolean state)
    {
        this.token = token;
        this.time = time;
        this.label = label;
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
