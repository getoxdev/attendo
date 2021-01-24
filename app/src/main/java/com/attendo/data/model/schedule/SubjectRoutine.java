package com.attendo.data.model.schedule;

public class SubjectRoutine {
    private String subjectName;
    private String time;
    private String instructor;

    public SubjectRoutine(String subjectName, String time, String instructor) {
        this.subjectName = subjectName;
        this.time = time;
        this.instructor = instructor;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}