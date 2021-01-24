package com.attendo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

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

    @SerializedName("__v")
    @Expose
    private int __v;

    @SerializedName("class")
    @Expose
    private String classId;

    public Boolean getCr() {
        return isCr;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int get__v() {
        return __v;
    }

    public String getClassId() {
        return classId;
    }
}
