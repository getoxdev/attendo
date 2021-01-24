package com.attendo.data.model;

public class JoinClass {

    private String classCode;
    private String name;
    private String email;
    private String scholarId;

    public JoinClass(String classCode, String name, String email, String scholarId) {
        this.classCode = classCode;
        this.name = name;
        this.email = email;
        this.scholarId = scholarId;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScholarid(String scholarId) {
        scholarId = scholarId;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getScholarid() {
        return scholarId;
    }
}
