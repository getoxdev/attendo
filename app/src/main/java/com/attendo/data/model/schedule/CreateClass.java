package com.attendo.data.model.schedule;

public class CreateClass {
    private String studentName;
    private String email;
    private String className;
    private String ScholarId;

    public CreateClass(String studentName, String email, String className, String scholarId) {
        this.studentName = studentName;
        this.email = email;
        this.className = className;
        ScholarId = scholarId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setScholarId(String scholarId) {
        ScholarId = scholarId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getClassName() {
        return className;
    }

    public String getScholarId() {
        return ScholarId;
    }
}
