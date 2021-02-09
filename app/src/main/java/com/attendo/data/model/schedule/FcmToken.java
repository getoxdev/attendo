package com.attendo.data.model.schedule;

public class FcmToken {

    String email;
    String fcm_token;

    public FcmToken(String email, String fcm_token) {
        this.email = email;
        this.fcm_token = fcm_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }
}
