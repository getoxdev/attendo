package com.attendo.data.model.schedule;

public class ResponseFcm {

    private String status;
    private String Fcm;

    public ResponseFcm(String status, String fcm) {
        this.status = status;
        Fcm = fcm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFcm() {
        return Fcm;
    }

    public void setFcm(String fcm) {
        Fcm = fcm;
    }
}
