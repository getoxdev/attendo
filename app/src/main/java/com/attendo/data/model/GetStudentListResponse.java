package com.attendo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStudentListResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("students")
    @Expose
    private List<Student> students;

    public String getStatus() {
        return status;
    }

    public List<Student> getStudents() {
        return students;
    }
}
