package com.attendo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetSchedule {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("requiredSchedule")
    @Expose
    private List<SubjectDetails> requiredSchedule;

    public ResponseGetSchedule(String status, List<SubjectDetails> requiredSchedule) {
        this.status = status;
        this.requiredSchedule = requiredSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubjectDetails> getRequiredSchedule() {
        return requiredSchedule;
    }

    public void setRequiredSchedule(List<SubjectDetails> requiredSchedule) {
        this.requiredSchedule = requiredSchedule;
    }
}
