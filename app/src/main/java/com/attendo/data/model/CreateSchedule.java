package com.attendo.data.model;

import com.google.gson.annotations.SerializedName;

public class CreateSchedule
{
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("token")
    private String token;

    @SerializedName("schedule")
    private Days days;

    public CreateSchedule(String code, String name, String token, Days days)
    {
        this.code = code;
        this.name = name;
        this.token = token;
        this.days = days;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }
}
