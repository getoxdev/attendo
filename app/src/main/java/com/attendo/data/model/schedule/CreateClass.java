package com.attendo.data.model.schedule;

public class CreateClass {
    private String studentName;
    private String email;
    private String className;
    private String scholarId;
    private String fcmToken;

    public CreateClass(String studentName, String email, String className, String scholarId, String fcmToken) {
        this.studentName = studentName;
        this.email = email;
        this.className = className;
        this.scholarId = scholarId;
        this.fcmToken = fcmToken;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
