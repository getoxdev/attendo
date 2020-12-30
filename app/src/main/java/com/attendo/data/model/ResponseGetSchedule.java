package com.attendo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetSchedule {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("requiredSchedule")
    @Expose
    private SubjectDetails requiredSchedule;

    public ResponseGetSchedule(String status, SubjectDetails requiredSchedule) {
        this.status = status;
        this.requiredSchedule = requiredSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubjectDetails getRequiredSchedule() {
        return requiredSchedule;
    }

    public void setRequiredSchedule(SubjectDetails requiredSchedule) {
        this.requiredSchedule = requiredSchedule;
    }
}
