package com.attendo.model;

import com.google.gson.annotations.SerializedName;

public class Reminder {

    @SerializedName("TimeStamp")
    private String timestamp;

    @SerializedName("fcmToken")
    private String fcmToken;


    public Reminder(String timestamp, String fcmToken) {
        this.timestamp = timestamp;
        this.fcmToken = fcmToken;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}
