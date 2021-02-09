package com.attendo.data.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {
    @SerializedName("isCr")
    @Expose
    private Boolean isCr;

    @SerializedName("_id")
    @Expose
    private String studentId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("scholarId")
    @Expose
    private String scholarId;

    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;

    @SerializedName("class")
    @Expose
    private String classId;

    public UserDetails(Boolean isCr, String studentId, String name, String email, String scholarId, String fcmToken, String classId) {
        this.isCr = isCr;
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.scholarId = scholarId;
        this.fcmToken = fcmToken;
        this.classId = classId;
    }

    public Boolean getCr() {
        return isCr;
    }

    public void setCr(Boolean cr) {
        isCr = cr;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
