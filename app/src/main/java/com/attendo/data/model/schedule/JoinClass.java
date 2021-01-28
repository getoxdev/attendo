package com.attendo.data.model.schedule;

public class JoinClass {

    private String classCode;
    private String name;
    private String email;
    private String scholarId;
    private String fcmToken;

    public JoinClass(String classCode, String name, String email, String scholarId, String fcmToken) {
        this.classCode = classCode;
        this.name = name;
        this.email = email;
        this.scholarId = scholarId;
        this.fcmToken = fcmToken;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScholarId() {
        return scholarId;
    }

    public void setScholarId(String scholarId) {
        this.scholarId = scholarId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
