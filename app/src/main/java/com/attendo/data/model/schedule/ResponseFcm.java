package com.attendo.data.model.schedule;

public class ResponseFcm {

    private String status;
    private String fcmToken;

    public ResponseFcm(String status, String fcm) {
        this.status = status;
        fcmToken = fcm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFcm() {
        return fcmToken;
    }

    public void setFcm(String fcm) {
        fcmToken = fcm;
    }
}
